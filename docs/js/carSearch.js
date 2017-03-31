


$.getJSON('http://getstartedtomcat-uncorpulent-patriarchship.mybluemix.net/vehicles/tags', { get_param: 'value' }, function(data) {
	countries = data;
	var cList = $('#tagList')
	$.each(countries, function(i)
	{
		if (!("cost" == countries[i].substring(0, 4))){

			var li = $('<div/>')
			.addClass('col-md-2 tCenter')
			.appendTo(cList);
			var aaa = $('<span/>')
			.addClass('badge badge-default tagD  animated')
			.text(countries[i])
			.click(function() {
  			//alert( "Handler for " + countries [i] + " called." );
  			if (aaa.hasClass( "rubberBand" )){
  				aaa.css("background", "red");
  				aaa.removeClass("rubberBand");
  			}
  			else if (aaa.css("background-color") == "rgb(255, 0, 0)"){
  				aaa.css("background", "gray");
  			}
  			else{
  				aaa.css("background", "green");
  				aaa.addClass("rubberBand");
  			}
  		}
  		)
			.appendTo(li);
		}
	});
});

function doRequest(){
	var aaa = $(".sitelogo");
  	if (aaa.hasClass( "rotateIn" )) aaa.removeClass("rotateIn");
  	if (aaa.hasClass( "tada" )) aaa.removeClass("tada");
	wanted = "";
	unWanted = "";
	var tagList = $('.tagD')
	$('.tagD').each(function(i, obj) {
		if ($(this).hasClass( "rubberBand" )){
			wanted += $(this).text() + ";";
		}
		else if ($(this).css("background-color") == "rgb(255, 0, 0)"){
  				unWanted += $(this).text() + ";";
  				}


	});
	setTimeout(function(){
		aaa.addClass("tada");
	}, 100);
  	
	var budget = $('#budgetinput').val()
	$.getJSON('http://getstartedtomcat-uncorpulent-patriarchship.mybluemix.net/vehicles/search?budget=' + budget + '&wantFields=' + wanted + '&dontWantField=' + unWanted, { get_param: 'value' }, function(data) {
		
		var cList = $('#carList')
		cList.html('');
		localData = [{"manufacturer":"chevrolet", "gasPerYear":"2200.00","model":"k1500 pickup 4wd","image":"http://cgi.chevrolet.com/mmgprod-us/dynres/prove/image.gen?i=2017/CK15543/CK15543__2LZ/G1K_H2U_RD1gmds6.jpg&v=deg01&std=true&country=US","cost":1055.0},
		{"manufacturer":"chevrolet", "gasPerYear":"2100.00","model":"avalanche 1500 awd","image":"https://img2.carmax.com/img/vehicles/14149635/1/320.jpg","cost":1360.0},
		{"manufacturer":"chevrolet", "gasPerYear":"2200.00","model":"k1500 pickup 4wd","image":"http://cgi.chevrolet.com/mmgprod-us/dynres/prove/image.gen?i=2017/CK15543/CK15543__2LZ/G1K_H2U_RD1gmds6.jpg&v=deg01&std=true&country=US","cost":1055.0},
		{"manufacturer":"chevrolet", "gasPerYear":"2100.00","model":"avalanche 1500 awd","image":"https://img2.carmax.com/img/vehicles/14149635/1/320.jpg","cost":1360.0},
		{"manufacturer":"chevrolet", "gasPerYear":"2200.00","model":"k1500 pickup 4wd","image":"http://cgi.chevrolet.com/mmgprod-us/dynres/prove/image.gen?i=2017/CK15543/CK15543__2LZ/G1K_H2U_RD1gmds6.jpg&v=deg01&std=true&country=US","cost":1055.0},
		{"manufacturer":"chevrolet", "gasPerYear":"2100.00","model":"avalanche 1500 awd","image":"https://img2.carmax.com/img/vehicles/14149635/1/320.jpg","cost":1360.0}];
		if (data.length == 0){
			var li = $('<div/>')
			.addClass('col-md-12 tCenter carHolder animated bounceInDown')
			.appendTo(cList);
			var aaa = $('<h2/>')
			.text("no results :'(")
			.addClass('img-fluid animated  rounded img-thumbnail')
			.appendTo(li);

		}
		$.each(data, function(i){
			var li = $('<div/>')
			.addClass('col-md-4 tCenter carHolder animated bounceInDown')
			.appendTo(cList);
			var aaa = $('<img/>')
			.addClass('img-fluid animated  rounded img-thumbnail')
			.attr("src",data[i].image)
			.appendTo(li);
			var aaa = $('<h2/>')
			.text(jsUcfirst(data[i].manufacturer))
			.addClass('img-fluid animated  rounded img-thumbnail')
			.appendTo(li);
			var aaa = $('<h3/>')
			.text(jsUcfirst(data[i].model))
			.addClass('img-fluid animated  rounded img-thumbnail')
			.appendTo(li);
			var aaa = $('<h4/>')
			.text("$" + data[i].cost + " CAD")
			.addClass('img-fluid animated  rounded img-thumbnail')
			.appendTo(li);
			var aaa = $('<h6/>')
			.text("$" + data[i].gasPerYear + " CAD in gas/year")
			.addClass('img-fluid animated  rounded img-thumbnail')
			.appendTo(li);
			var ccc = $('<div/>')
			.addClass('row')
			.css("padding", "10px")
			.appendTo(li);
			$.each(data[i].tags, function(j){
			var bbb = $('<div/>')
			.addClass('tCenter')
  			.css("margin", "5px")
			.appendTo(ccc);
			var col = "gray";
			if (wanted.indexOf(data[i].tags[j]) != -1) col = "green";
			var aaa = $('<span/>')
			.text(data[i].tags[j])
			.addClass('badge badge-default')
  			.css("background", col)
			.appendTo(bbb);
			});
		});
	});
	
}

function jsUcfirst(string) 
{
    return string.charAt(0).toUpperCase() + string.slice(1);
}
