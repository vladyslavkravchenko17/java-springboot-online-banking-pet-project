$(document).ready(function () {
    $(".block").slice(0, 6).show();
    if ($(".block:hidden").length != 0) {
        $("#load").show();
    }
    $("#load").on("click", function (e) {
        e.preventDefault();
        $(".block:hidden").slice(0, 6).slideDown();
        if ($(".block:hidden").length == 0) {
            $("#load").text("")
                .fadOut("slow");
        }
    });
})

$(function() {
    $("#" + $("#select option:selected").val()).show();
    $("#select").change(function(){
        $(".select-blocks").hide();
        $("#" + $(this).val()).show();
    });
});

$(document).ready(function () {
    $('#asking').on('change', function (e) {
        var value = parseInt($(this).val(), 10)
        commission = value * .03;

        $('#com').text(commission);
        $('#tot').text(value + commission);
    });
});

function showPlannedPayments() {
    var x = document.getElementById("plannedPayments");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function showCredits() {
    var x = document.getElementById("credits");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function showDeposits() {
    var x = document.getElementById("deposits");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function premature_return(node) {
    return confirm("Are you sure? You will not get additional percents for the deposit.");
}

function premature_repay(node) {
    return confirm("Are you sure? You will pay all the sum.");
}

const deposits = document.getElementById('deposits');
deposits.style.display = 'none'

const credits = document.getElementById('credits');
credits.style.display = 'none'

const plannedPayments = document.getElementById('plannedPayments');
plannedPayments.style.display = 'none'






