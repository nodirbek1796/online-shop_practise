function order(e){
    console.log(e.target.value)

    $.ajax({
        url: "/admin/rule/delete",
        type: "POST",
        data: {

        },
        success: function (response) {

            if (response === true) {
                location.reload();
            }
        },
        error: function (request, status, error) {

        }
    });
}