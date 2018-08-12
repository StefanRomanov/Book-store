
$(document).ready(function() {
    $('#select-platform').multiselect({
        buttonWidth: '200px',
        buttonClass: 'btn btn-info'
    });

});
$(document).ready(function() {
    $('#select-genre').multiselect({
            buttonWidth: '200px'
        }
    );
});
$(document).ready(function() {
    $('#select-rating').multiselect({
            buttonWidth: '100px'
        }
    );
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