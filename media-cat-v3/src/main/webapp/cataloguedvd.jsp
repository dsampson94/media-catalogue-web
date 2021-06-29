<%@page import="za.co.sfy.domain.ApplicationSettings"%>
<%@page import="za.co.sfy.domain.DVD"%>
<%@page import="za.co.sfy.domain.MediaType"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Media Catalogue V3</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
	<div id="container">
		<div class="w3-row" id="topbar">
			<div class="w3-third">
				<a><img src="Images/mediaCatImg.png" /></a>
			</div>
			<div class="w3-twothird" style="text-decoration: none;" id="navBar">
				<a href="index.jsp" id="navelement">Home</a> <a href="about.jsp"
					id="navelement">About</a> <a href="cataloguehome.jsp"
					id="navelement">Catalogue</a> <a href="websettings.jsp" id="navelement">Web-Settings</a>
			</div>
		</div>
		<div id="midbar">
			<div id="midblock">
				<h1>Catalogue</h1>
				<hr>
				<div id="upperblock">
					<div id="topheader">Add Item</div>
					<div
						style="height: 35px; font-size: 12px; vertical-align: middle; margin: 1px;">
						<form action="server" method="POST">
							<input type="radio" id="dvd"
								style="vertical-align: middle; margin: 0px; margin-left: -1px;"
								name="tablebut" value="adddvd" onclick="this.form.submit()">
							<label for="dvd">DVD</label><br> <input type="radio" id="cd"
								style="vertical-align: middle; margin: 0px; margin-left: -10px;"
								name="tablebut" value="addcd" onclick="this.form.submit()">
							<label for="cd">CD</label>
						</form>
					</div>
				</div>
				<div id="lowerblock">
					<div id="lowheader">Add DVD</div>
					<form action="server" method="POST">
						<div id="formdiv">
							<div class="w3-half">
								<label><b>Title</b></label><br> <input type="text"
									placeholder="Title" name="titlefielddvdadd" required><br>
								<label><b>Duration</b></label><br> <input type="text"
									placeholder="Duration" name="lengthfielddvdadd" required><br>
								<label><b>Genre</b></label><br> <input type="text"
									placeholder="Genre" name="genrefielddvdadd" required><br>
							</div>
							<div class="w3-half">
								<label><b>Lead Actor</b></label><br> <input type="text"
									placeholder="Lead Actor" name="leadactorfielddvdadd" required><br>

								<label><b>Lead Actress</b></label><br> <input type="text"
									placeholder="Lead Actress" name="leadactressfielddvdadd"
									required><br>
								<button type="submit" name="tablebut" value="submitdvdadd">Add</button>
							</div>
						</div>
					</form>
				</div>
				<hr>
			</div>
		</div>
		<div id="footerbar">
			<p id="footerText">© 2020 Media Catalogue</p>
		</div>
	</div>
</body>
<% ApplicationSettings appSett = (ApplicationSettings) request.getSession().getAttribute("ApplicationSettings"); %>
<style>
html{
    --theme-color-1: <%= appSett.getBackgroundColour1() %>;
    --theme-color-2: <%= appSett.getBackgroundColour2() %>;
    --font-style: <%= appSett.getFontStyle() %>;
}
body {
	font-family: var(--font-style);
	font-size: 15px;
}

#container {
	margin: 5% auto 5% auto;
	padding: 5px 5px 5px 5px;
	border: 5px solid white;
	width: 50%;
	overflow: auto;
	box-shadow: 2px 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
	text-align: center;
	position: relative;
}

#topbar {
	background-color: var(--theme-color-1);
	width: 100%;
	height: 61px;
	position: relative;
	overflow: hidden;
}

#midbar {
	background-color: var(--theme-color-2);
	width: 100%;
	height: 320px;
	justify-content: center;
	align-items: center;
	display: flex;
}

#footerbar {
	background-color: var(--theme-color-1);
	width: 100%;
	height: 55px;
}

#navBar {
	font-size: 19px;
	color: white;
	margin-top: 18px;
	display: inline-block;
}

#footerText {
	color: white;
	font-size: 16px;
	position: absolute;
	left: 10px;
}

#midblock {
	width: 60%;
	height: 90%;
	background-color: #D3D3D3;
	margin: 0% auto 0% auto;
	padding: 5px 5px 5px 5px;
	border: 1px solid black;
	border-radius: 25px;
	position: relative;
	overflow: hidden;
}

hr {
	height: 0px;
	border: none;
	border-top: 1px solid black;
	margin: 7px 7px 7px 7px;
}

#navelement {
	text-decoration: none;
	padding: 15px;
}

button {
	background-color: var(--theme-color-1);
	border: none;
	color: white;
	padding-left: 10px;
	padding-right: 10px;
	transition-duration: 0.4s;
	font-size: 16px;
	margin-top: 12px;
}

button:hover {
	background-color: var(--theme-color-2);
	color: white;
}

#navelement:hover {
	text-decoration: underline;
}

h1 {
	text-align: left;
	margin: 4px 10px 2px 10px;
	font-size: 12px;
}

#upperblock {
	height: 65px;
	background-color: #B0B0B0;
	margin: 6px 120px 6px 120px;
}

#lowerblock {
	height: 148px;
	background-color: #B0B0B0;
	margin: 6px 10px 6px 10px;
}

#topheader {
	top: 0;
	height: 25px;
	background-color: #A0A0A0;
}

#lowheader {
	top: 0;
	background-color: #A0A0A0;
	height: 25px;
}

#formdiv {
	font-size: 11px;
}
</style>
</html>
