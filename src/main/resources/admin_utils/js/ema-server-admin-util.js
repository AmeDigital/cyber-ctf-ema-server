function create_new_user(){
		var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://my-admin-server.backend.emaserver.com', true);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.onload = function () {
            console.log("Usuário foi criado? " + this.responseText);
        };
        xhr.send('user=admin-ema-server&pwd=FLAG#7_{D0_N07_EXP0S3_N0T_PR0DUCTION_FILES}&organization=EMA');
	}