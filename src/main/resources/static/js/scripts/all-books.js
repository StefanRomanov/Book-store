$(document).ready(function () {
    fetchData(0)
});

function setSearchItems(total,current) {
    $('#current-page').val(current);
    if(total > 0){
        $('#total-pages').text(total);
    } else {
        $('#total-pages').text(1);
    }

    if(current === 1){
        $('#previous-page').prop( "disabled", true );
    } else {
        $('#previous-page').prop( "disabled", false );
    }

    if(current >= total){
        $('#next-page').prop( "disabled", true );
    } else {
        $('#next-page').prop( "disabled", false );
    }
}

$("#current-page").change(function () {
    fetchData(($(this).val() - 1))
});

$('#previous-page').click(function () {
    let element = $('#current-page');
    let currentPage = $(element).val();

    if(parseInt(currentPage) === 1){
        return
    }

    $(element).val(currentPage - 1);
    $(element).trigger('change');
});

$('#next-page').click(function () {
    let element = $('#current-page');
    let nextPage = $('#total-pages').text();
    let currentPage = $(element).val();

    console.log(nextPage);
    console.log(currentPage);

    if(parseInt(currentPage) === parseInt(nextPage)){
        return
    }

    $(element).val(1 + parseInt(currentPage));
    $(element).trigger('change');
});

function fetchData(pageNumber) {
    $('#result').empty();

    $.ajax({
        type: 'GET',
        url:'http://localhost:8080/books/api/all?page='+ pageNumber,
        success: function (data) {

            setSearchItems(data['totalPages'],data['number'] + 1);

            $.get('/templates/book-list-element.html', function (template){

                let renderedElement = template;

                let repeatableElements = ['id', 'title', 'releaseDate', 'authorName', 'reviewScore', 'price', 'coverImageUrl'];

                if(data['content'].length > 0){
                    for(const object of data['content']){
                        for (const element of repeatableElements) {
                            while(renderedElement.indexOf('{{'
                                + element
                                + '}}') >= 0) {

                                let valueToReplace = object[element];

                                if(element.toLowerCase().indexOf('time') > 0) {
                                    valueToReplace = valueToReplace.replace('T', ' | ');
                                }

                                renderedElement =
                                    renderedElement.replace('{{'
                                        + element
                                        + '}}', valueToReplace);
                            }
                        }
                    }

                    $('#result').append(renderedElement)
                }


            })
        },
        error: function (err) {
            console.log(err);
        }
    })
}