window.onload = () => {
    const p = document.getElementById( "pwd" );

    const b = document.querySelector("form>p>span");

    b.onclick = e => {
	e.preventDefault();
	if( p.type === "password" ){
	    p.type = "text";
	    b.textContent = "Hide";
	}
	else{
	    p.type = "password";
	    b.textContent = "Show";
	}
    };
}
