/*
 * @author Viraj Shah
 *
 */
$(document).ready(function() {
    // Adding event listner to zipcode field
    $("input[name='zip']").change(function() {
	setCityStateField($("input[name='zip']").val());
    });

    // adding event listner to reset button
    $("button#reset_button").click(function() {
	resetForm();
    });

    $("input#submit_button").click(function() {
	validate();
    });

    $("a#wrong_user_link").click(function() {
	wrongUser();
    });

    $("input[name='name']").blur(function() {
	validateName($("input[name='name']").val());
    });
    $("input[name='address']").blur(function() {
	validateAddress($("input[name='address']").val());
    });
    $("input[name='data']").blur(function() {
	validateData();
    });
    $("input[name='id']").blur(function() {
	validateId($("input[name='id']").val());
    })
});

// Validate name field
function validateName(name) {
    $(".name_error").hide();
    var letters_regex = /^[a-zA-Z ]*$/;
    if (!name.match(letters_regex)) {
	$("input[name='name']").parent().append(
		"<div class = name_error>Only alphabets allowed</div>");
	return false;
    } else
	return true;
}

// Validate address field
function validateAddress(addr) {
    $(".address_error").hide();
    var address_regex = /^[a-zA-Z\s\d\/]*\d[a-zA-Z\s\d\/]*$/;
    if (!addr.match(address_regex)) {

	$("input[name='address']").parent().append(
		"<div class = address_error>Enter a valid address</div>");
	return false;
    } else
	return true;
}
// Validate Data Field
function validateData() {
    $("#data_error").hide();
    var data_values = null;
    var data_dom = document.forms["survey_form1"]["data"];
    data_values = data_dom.value;
    var data_array = data_values.split(",");
    if (data_values == "" || data_values == null) {
	$("#data_error").html("10 values must be filled out");
	$("#data_error").show();
	return false;
    }
    else if (data_array.length < 10 || data_array[data_array.length - 1] == "") {
	$("#data_error").html("Atleast 10 values must be filled out");
	$("#data_error").show();
	return false;
    }
    else
	return true;
}
function validateId(id) {
    $("#id_error").hide();
    if(id ==="" || id===null) {
	$("#id_error").html("ID cannot be blank").show();
	return false;
    }
	
    return true;
}

function validateZip(zip) {
    if (zip == '' || zip == null)
	return false;
    else
	return true;
}

function greeting() {
    var hours = (new Date()).getHours();
    var greeting_message;
    if (hours < 12)
	greeting_message = "Good Morning, ";
    else if (hours >= 12 && hours < 18)
	greeting_message = "Good Afternoon, ";
    else
	greeting_message = "Good Evening, ";

    var cname = getCookie("name");
    if (cname != "") {
	document.getElementById("greeting_message").innerHTML = greeting_message
		+ cname;
	// document.writeln(cname);
    } else {
	cname = setNameCookie();
	if (typeof cname === 'undefined')
	    $("#greeting_message").html(greeting_message + "unknown user");
	else
	    $("#greeting_message").html(greeting_message + cname);
    }
}

function wrongUser() {
    setNameCookie();
    location.reload();
}

function setNameCookie() {
    var nameValue = prompt("Please enter your name:", "");
    if (nameValue != "" && nameValue != null) {
	var dt = new Date();
	dt.setTime(dt.getTime() + (365 * 24 * 60 * 60 * 1000));
	var expires = "expires=" + dt.toGMTString();
	document.cookie = "name=" + escape(nameValue) + "; " + expires;
	return nameValue;
	// document.writeln(name);
    }
}

function getCookie(name) {
    var key = name + "=";
    var cookieArray = unescape(document.cookie).split(';');
    for (var i = 0; i < cookieArray.length; i++) {
	var ch = cookieArray[i];
	while (ch.charAt(0) == ' ')
	    ch = ch.substring(1, ch.length - 1);
	if (ch.indexOf(key) == 0)
	    return ch.substring(key.length, ch.length);
    }
    return "";
}

function removeErrors() {
    $("#data_error").hide();
    $(".error").hide();
}

// function hideAvgMax() {
// $("#calculated_avg_row").hide();
// $("#calculated_max_row").hide();
// }
//
// function processDataField() {
// else {
// // Converting data_array to Int
// for (var i = 0; i < data_array.length; i++)
// data_array[i] = parseInt(data_array[i]);
//
// for (var i = 0; i < data_array.length; i++) {
// if (data_array[i] < 0 || data_array[i] > 100) {
// $("#data_error").html(
// "Invalid value, enter numbers between 1 to 100");
// hideAvgMax();
// // alert("Invalid value, enter numbers between 1 to 100");
// return;
// }
// }
// var avg = calculateAverage(data_array);
// var max = calculateMax(data_array);
// if ((avg != null || avg != "") && (max != null || max != "")) {
// $("#calculated_avg_value").html(avg);
// $("#calculated_max_value").html(max);
// $("#calculated_avg_row").show();
// $("#calculated_max_row").show();
// // data_dom.value = "Average = " + avg + "\nMaximum = " + max;
// } else
// alert("Invalid value entered!");
// }
// }
//
// function calculateAverage(values) {
// var sum = 0;
// for (var i = 0; i < values.length; i++)
// sum += values[i];
// var avg = sum / values.length;
// return avg;
// }
//
// function calculateMax(values) {
// var max = -1;
// for (var i = 0; i < values.length; i++)
// max = (values[i] > max) ? values[i] : max;
// return max;
// }

function validate() {
    var isValid = true;
    // Removing the existing error
    removeErrors();
    /*
     * Note: E-mail validation has been provided by html input type 'email'
     */
    // Validating name
    if (!validateName($("input[name='name']").val())) {
	isValid = false;
    }

    // Validating address
    if (!validateAddress($("input[name='address']").val())) {
	isValid = false;
    }
    // Validating data field
    if (!validateData()) {
	isValid = false;
    }
    //Validate ID
    if(!validateId($("input[name='id']").val()))
	isValid = false;

    // Validating Checkboxes
    if ($("input[name='preference']").serializeArray().length < 2) {
	isValid = false;
	$('#survey_info_label1').after(
		"<div class = error>Check atleast 2 boxes</div>");
    }
    // Validate Radio button
    if ($("input[name='found']").serialize() == ""
	    || $("input[name='found']").serialize() == null) {
	isValid = false;
	$('#survey_info_label2').after(
		"<div class = error>Select atleast 1 option</div>");
    }
    if (!isValid) {
	$("#error_modal").dialog({
	    modal : true,
	    buttons : {
		Ok : function() {
		    $(this).dialog("close");
		}
	    }
	});
	$("#error_modal p").show();
    }

    return isValid;
}

function resetForm() {
    $("[name='survey_form1']")[0].reset();
    removeErrors();
    $("#calculated_avg_row").hide();
    $("#calculated_max_row").hide();
}

function setCityStateField(zip) {
    $("div#zip_error").html("");
    $("div#zip_error").hide();
    var found = false;
    // XMLHttpRequest reqObj;
    var reqObj;
    try {
	reqObj = new XMLHttpRequest();
    } catch (e) {
	alert('Ajax error');
    }
    var url = "location_json.json";
    reqObj.onreadystatechange = function() {
	if (reqObj.readyState == 4 && reqObj.status == 200) {
	    // Javascript function JSON.parse to parse JSON data
	    var jsonObj = JSON.parse(reqObj.responseText);
	    for (var i = 0; i < jsonObj.zipcodes.length; i++)
		if (jsonObj.zipcodes[i].zip == zip) {
		    found = true;
		    $("input[name='city']").html(jsonObj.zipcodes[i].city);
		    $("input[name='state']").html(jsonObj.zipcodes[i].state);
		    break;
		}
	    if (!found) {
		$("div#zip_error").html("Invalid zipcode");
		$("div#zip_error").show();
		$("input[name='city']").html("");
		$("input[name='state']").html("");
	    }
	}
    }
    reqObj.open("GET", "location_json.json", true);
    reqObj.send(null);

}