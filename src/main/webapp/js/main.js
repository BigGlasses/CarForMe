'use strict';

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }



function ValidateForm() {
    var manufacturer = document.forms["myForm"]["manufacturer"].value;
    var budget = document.forms["myForm"]["budget"].value;
    var KMdriven = document.forms["myForm"]["KMdriven"].value;
    
    if (budget == null || budget == "") {
        alert("You must enter a budget");
        return false;
    }
}
function printCars() {
    var manufacturer = document.forms["myForm"]["manufacturer"].value;
    var budget = document.forms["myForm"]["budget"].value;
    var KMdriven = document.forms["myForm"]["KMdriven"].value;
    $.getJSON("http://localhost:8080/vehicles/search?budget="+ budget + "&manufacturer=" + manufacturer, function(data) {
    data.forEach(function(data){
    document.write(data.model + " " +  data.make);
    document.write("------");
});
    //data is the JSON string
});
    //document.write(SelectedRows[0].manufacturer + "   -   " + SelectedRows[0].model + "   -   " + SelectedRows[0].cost + "   -   " + SelectedRows[0].gasPY + "<img src='https://images.honda.ca/models/H/Models/2017/accord_sedan/touring_v6_10393_240white_orchid_pearl_front.png?width=500' height=\"200\" width=\"200\"/>");
    //document.write(SelectedRows[1].manufacturer + "   -   " + SelectedRows[1].model + "   -   " + SelectedRows[1].cost + "   -   " + SelectedRows[1].gasPY + "<img src='https://images.honda.ca/models/H/Models/2017/accord_sedan/touring_v6_10393_240white_orchid_pearl_front.png?width=500' height=\"200\" width=\"200\"/>");
    //document.write(SelectedRows[2].manufacturer + "   -   " + SelectedRows[2].model + "   -   " + SelectedRows[2].cost + "   -   " + SelectedRows[2].gasPY + "<img src='https://images.honda.ca/models/H/Models/2017/accord_sedan/touring_v6_10393_240white_orchid_pearl_front.png?width=500' height=\"200\" width=\"200\"/>");

}

var SelectedRows=   [
    { manufacturer: "Honda", model: "Accord", cost: "10000", gasPY: "1000", imagelink: "" },
{manufacturer:"Honda",model:"Civic",cost:"7799",gasPY:"20000",imagelink:""},
{ manufacturer: "Honda", model: "Pilot", cost: "30000", gasPY: "3000", imagelink: "" }
]





//ReactDOM.render(React.createElement(ShoppingList, { name: 'Tony' }), document.getElementById('container'));