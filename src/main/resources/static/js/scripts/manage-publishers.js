$(document).ready(function () {
    $('#not-approved').attr('checked','checked');
    let approved = setApproved();
    let companyName = setCompanyName();

    fetchData(0, approved, companyName)
});

$('#search').click(function(event){
    event.preventDefault();
    let approved = setApproved();
    let title = setCompanyName();
    fetchData(0,approved,title)
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

    if(parseInt(currentPage) === parseInt(nextPage)){
        return
    }

    $(element).val(1 + parseInt(currentPage));
    $(element).trigger('change');
});

function fetchData(pageNumber,approved,company) {
    $('#result').empty();

    let url = 'http://localhost:8080/publishers/api/manage?page='+ pageNumber + '&approved=' + approved;

    if(company != null || company === ''){
        url = url + '&company=' + company;
    }

    $.ajax({
        type: 'GET',
        url:url,
        success: function (data) {

            setSearchItems(data['totalPages'],data['number'] + 1);

            $.get('/templates/publisher-list-element.html', function (template){

                let renderedElement = template;

                console.log(data);

                let repeatableElements = ['id', 'companyName', 'country', 'vatNumber'];

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
                        $('#result').append(renderedElement)
                        renderedElement = template;
                    }
                }
            })
        },
        error: function (err) {
            console.log(err);
        }
    })
}

function setApproved() {
    if($('#approved').is(':checked')){
        return true;
    } else if($('#not-approved').is(':checked')) {
        return false
    }
}

function setCompanyName() {
    return $('#search-text').val();
}

$('input[name=approve]').change(function () {
    fetchData(0,setApproved(),setCompanyName())
});