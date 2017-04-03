"use strict";

var Input = React.createClass({
  displayName: "Input",

  render: function render() {
    return React.createElement(
      "div",
      { className: "Input" },
      React.createElement("input", { id: this.props.manufacturer, autoComplete: "false", required: true, type: this.props.type, placeholder: this.props.placeholder }),
      React.createElement("label", { "for": this.props.manufacturer })
       //document.write(this.props.manufacturer);
        //document.write("------");
    );
  }
});


var CarForm = React.createClass({
  displayName: "Modal",
  propTypes: {
    value: React.PropTypes.object.isRequired,
    onChange: React.PropTypes.func.isRequired,
    onSubmit: React.PropTypes.func.isRequired,
  },
  
  onManufacturerChange: function(e) {
    this.props.onChange(Object.assign({}, this.props.value, {manufacturer: e.target.value}));
  },

  onNameChange: function(e) {
    this.props.onChange(Object.assign({}, this.props.value, {name: e.target.value}));
  },
  
  onEmailChange: function(e) {
    this.props.onChange(Object.assign({}, this.props.value, {email: e.target.value}));
  },
  
  onDescriptionChange: function(e) {
    this.props.onChange(Object.assign({}, this.props.value, {description: e.target.value}));
  },

  onSubmit: function(e) {
    e.preventDefault();
    this.props.onSubmit();
  },


  render: function() {
    var errors = this.props.value.errors || {};

    return (

      React.createElement('form', {onSubmit: this.onSubmit, className: 'Modal', noValidate: true},

        React.createElement("select", { value: this.props.value.manufacturer,className: "InputDROP", onChange: this.onManufacturerChange },
         React.createElement("option", { value: "Select a car manufacturer"}, "Select a car manufacturer"),
         React.createElement("option", { value: 22222 }, "22222")),


        React.createElement(Input, {
          type: 'text',
          className: "Input",
          placeholder: 'Budget($) - Required',
          value: this.props.value.name,
          onChange: this.onNameChange,
        }),
        React.createElement(Input, {
          type: 'email',
          className: "Input",
          placeholder: 'KM Driven Per Week ',
          value: this.props.value.email,
          onChange: this.onEmailChange,
        }),

        React.createElement('button', {type: 'submit'}, "Submit")
      )
    );
  },
});



var ContactItem = React.createClass({
  propTypes: {
    name: React.PropTypes.string.isRequired,
    email: React.PropTypes.string.isRequired,
    description: React.PropTypes.string,
    manufacturer: React.PropTypes.string,
  },

  render: function() {
    return (
      React.createElement('li', {className: 'ContactItem'},
        React.createElement('h2', {className: 'ContactItem-name'}, this.props.name),
        React.createElement('a', {className: 'ContactItem-email', href: 'mailto:'+this.props.email}, this.props.email),
        React.createElement('div', {className: 'ContactItem-description'}, this.props.description),
        React.createElement('div', {className: 'ContactItem-manufacturer'}, this.props.manufacturer)
      )
    );
  },
});


var ContactView = React.createClass({
  propTypes: {
    contacts: React.PropTypes.array.isRequired,
    newContact: React.PropTypes.object.isRequired,
    onNewContactChange: React.PropTypes.func.isRequired,
    onNewContactSubmit: React.PropTypes.func.isRequired,
  },

  render: function() {
    var contactItemElements = this.props.contacts
      .filter(function(contact) { return contact.email; })
      .map(function(contact) { return React.createElement(ContactItem, contact); });

    return (
      React.createElement('div', {className: 'ContactView'},
        React.createElement('h1', {className: 'ContactView-title'}, ""),
        React.createElement('ul', {className: 'ContactView-list'}, contactItemElements),
       // React.createElement('ul', {className: 'App'}, contactItemElements),
        React.createElement("div", { className: "App" },
          React.createElement(CarForm, {
          value: this.props.newContact,
          onChange: this.props.onNewContactChange,
          onSubmit: this.props.onNewContactSubmit,
        }))
      )
    );
  },
});


/*
 * Constants
 */


var CONTACT_TEMPLATE = {name: "", email: "", description: "", errors: null};



/*
 * Actions
 */


function updateNewContact(contact) {
  setState({ newContact: contact });
}


function submitNewContact() {
  var contact = Object.assign({}, state.newContact, {key: state.contacts.length + 1, errors: {}});
  
  if (!contact.name) {
    contact.errors.name = ["Please enter your new contact's name"];
  }
  if (!/.+@.+\..+/.test(contact.email)) {
    contact.errors.email = ["Please enter your new contact's email"];
  }

  setState(
    Object.keys(contact.errors).length === 0
    ? {
        newContact: Object.assign({}, CONTACT_TEMPLATE),
        contacts: state.contacts.slice(0).concat(contact),
      }
    : { newContact: contact }
  );
}


/*
 * Model
 */


// The app's complete current state
var state = {};

// Make the given changes to the state and perform any required housekeeping
function setState(changes) {
  Object.assign(state, changes);
  
  ReactDOM.render(
    React.createElement(ContactView, Object.assign({}, state, {
      onNewContactChange: updateNewContact,
      onNewContactSubmit: submitNewContact,
    })),
    document.getElementById('react-app')
  );
}

// Set initial data
setState({
  contacts: [
    {key: 1, test: "", email: "", description: ""},
    {key: 2, name: "", email: ""},
  ],
  newContact: Object.assign({}, CONTACT_TEMPLATE),
});
