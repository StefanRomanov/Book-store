$('#search').click(function (event) {
    event.preventDefault();
    let username = $('#search-text').val();
    window.location.href = "/users/roles?username=" + username;
});

$('#page').change(function () {

    let newPage = $('#page').val();
    window.location.href = "/users/roles?page=" +  (parseInt(newPage) - 1) + "&size=1";
});