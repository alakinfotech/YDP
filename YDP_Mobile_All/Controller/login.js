// JavaScript Document

$(document).ready(function(){
  $('#button_login').click(function(){
     $.mobile.showPageLoadingMsg("a", "Loading theme a...");
	 setTimeout('loadHomePage()', 2000);
	 
});
});

function loadHomePage(){
	$.mobile.hidePageLoadingMsg();
	$.mobile.changePage($(document.location.href="view/home.html"), 'fade');
	 
}

