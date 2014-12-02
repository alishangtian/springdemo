
function GetHttpRequest()

{

	if (window.XMLHttpRequest) // Gecko

		return new XMLHttpRequest();

	else if (window.ActiveXObject) // IE

		return new ActiveXObject("MsXml2.XmlHttp");

}

function LoadJS(sId, url) {

	var oXmlHttp = GetHttpRequest();

	oXmlHttp.onreadystatechange = function()

	{

		if (oXmlHttp.readyState == 4)

		{

			if (oXmlHttp.status == 200 || oXmlHttp.status == 304)

			{

				IncludeJS(sId, url, oXmlHttp.responseText);

			}

			else

			{

				alert('XML request error: ' + oXmlHttp.statusText + ' ('
						+ oXmlHttp.status + ')');

			}

		}

	};
	oXmlHttp.open('GET', url, true);
	oXmlHttp.send(null);
}

function IncludeJS(sId, fileUrl, source)

{

	if ((source != null) && (!document.getElementById(sId))) {

		var oHead = document.getElementsByTagName('HEAD').item(0);

		var oScript = document.createElement("script");

		oScript.language = "javascript";

		oScript.type = "text/javascript";

		oScript.id = sId;

		//oScript.defer = true;

		oScript.text = source;

		oHead.appendChild(oScript);
		
		//var targetNode = document.getElementsByTagName('script').item(2);
		
		//var targetNode = oHead.lastChild;
		
		//alert(targetNode);
		
		//oHead.insertBefore(oScript, targetNode);

	}

}

LoadJS("a_script", "./a.js");