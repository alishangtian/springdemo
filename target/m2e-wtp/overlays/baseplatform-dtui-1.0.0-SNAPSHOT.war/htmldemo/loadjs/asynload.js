function LoadJS( id, fileUrl ) 

{ 

    var scriptTag = document.getElementById( id ); 

    var oHead = document.getElementsByTagName('HEAD').item(0); 

    var oScript= document.createElement("script"); 



    if ( scriptTag  ) oHead.removeChild( scriptTag  ); 

    oScript.id = id; 

    oScript.type = "text/javascript"; 

    oScript.src=fileUrl ; 

    oHead.appendChild( oScript);     

} 

LoadJS("a_script", "./a.js");