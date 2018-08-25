

//$('.selectpicker').selectpicker({
//});
$('.datepicker').datepicker({
    format: 'dd-mm-yyyy'
});

$('#titleImage').change(function() {
    $(this).next('label').clone();
    let field = $('#titleImage');
    let file = field[0].files[0];

    if(file.size > 26214400){
        alert('Cover image file too big ! Maximum 25 MB allowed !');
        field.wrap('<form>').closest('form').get(0).reset();
        field.unwrap();

        field.stopPropagation();
        field.preventDefault();
    }

    let result = file.name;
    $(this).next('label').text(result);;
});

$('#otherImages').change(function() {
    $(this).next('label').clone();
    let field = $('#otherImages');
    let file = field[0].files[0];

    if(file.size > 26214400){
        alert('Text file too big ! Maximum 25 MB allowed !');
        field.wrap('<form>').closest('form').get(0).reset();
        field.unwrap();

        field.stopPropagation();
        field.preventDefault();
    }

    let result = file.name;
    $(this).next('label').text(result);
});