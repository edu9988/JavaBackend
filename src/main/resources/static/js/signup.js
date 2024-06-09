window.onload = () => {
    const p = document.getElementById( "pwd" );
    const rp = document.getElementById( "retype" );

    p.onkeyup = () => {
	if( p.value === rp.value )
	    rp.setCustomValidity( '' );
	else
	    rp.setCustomValidity( "Passwords don't match" );
    }

    rp.onkeyup = () => {
	if( p.value === rp.value )
	    rp.setCustomValidity( '' );
	else
	    rp.setCustomValidity( "Passwords don't match" );
    }

    const ls = document.querySelectorAll("form>p>span");

    ls[0].onclick = e => {
	e.preventDefault();
	if( p.type === "password" ){
	    p.type = "text";
	    ls[0].textContent = "Hide";
	}
	else{
	    p.type = "password";
	    ls[0].textContent = "Show";
	}
    };

    ls[1].onclick = e => {
	e.preventDefault();
	if( rp.type === "password" ){
	    rp.type = "text";
	    ls[1].textContent = "Hide";
	}
	else{
	    rp.type = "password";
	    ls[1].textContent = "Show";
	}
    };
}
