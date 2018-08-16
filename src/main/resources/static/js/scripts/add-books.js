

//$('.selectpicker').selectpicker({
//});
$('.datepicker').datepicker({
    format: 'dd-mm-yyyy'
});

$('#titleImage').change(function() {
    $(this).next('label').clone();
    let file = $('#titleImage')[0].files[0].name;
    $(this).next('label').text(file);
});

$('#otherImages').change(function() {
    $(this).next('label').clone();
    let count = $('#otherImages')[0].files.length;
    let result = '';
    if(count > 0){
        result = count + ' images selected !'
    }
    $(this).next('label').text(result);
});