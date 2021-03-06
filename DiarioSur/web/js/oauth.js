CLIENT_ID = '1012655994660-3mkachcu6j07nukrletrfkh3hnlqhi4j.apps.googleusercontent.com';
API_KEY = 'AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4';
DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/people/v1/rest"];
SCOPES = "https://www.googleapis.com/auth/contacts.readonly";

function handleClientLoad() {
    gapi.load('client:auth2', initClient);
}

function initClient() {
    gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
    }).then(function () {
        gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);
        updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        $('#authorize-button').click(handleAuthClick);
        $('#signout-button').click(handleSignoutClick);
    });
}

function updateSigninStatus(isSignedIn) {
    if (isSignedIn) {
        $('#nosession-group').css('display', 'none');
        $('#session-group').css('display', 'block');

        if ($.trim($('#user-pill').text()) === 'Guest') {
            var email = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail();
            var name = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getGivenName();
            var surname = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getFamilyName();
            $('#user-pill').text(email);
            window.location.replace('UserCRUD?email=' + email + '&name=' + name + '&surname=' + surname);
        }
    } else {
        $('#nosession-group').css('display', 'block');
        $('#session-group').css('display', 'none');

        if ($.trim($('#user-pill').text()) !== 'Guest') {
            $('#user-pill').text('Guest');
            window.location.replace('UserCRUD?logout');
        }
    }
}

function handleAuthClick(event) {
    gapi.auth2.getAuthInstance().signIn();
}

function handleSignoutClick(event) {
    gapi.auth2.getAuthInstance().signOut();
}
