$(document).ready(function() { 
    $('body').on('click','a.toframe',function(event) {
        event.preventDefault();
        var href=$(this).attr('href');
        $('#contentframe').attr('src', href);
    });
    $("#root").click(function() {
        $('div').removeClass("active");
        $(this).addClass("active");
        if (!$('#departments').length) {
            var request = $.ajax({
                url: '../get/departments',
                type: "GET",
                dataType: 'json'
            });
            request.done ( function (data) {
                var deps="<div id=\"departments\">";
                for(var i=0;i<data.length;i++){
                    var obj = data[i];
                    deps+="<div class=\"department\"><a class=\"toframe\" href=\"../../group/view/" + obj.id + "\">" + obj.departmentName + "</a></div>";
                    }
                deps+="</div>";
                $('#root').after(deps);
            });
            request.fail ( function () {
                $('#root').after("<div id=\"departments\">Server error</div>");
            });
        } else {
            $('#departments').remove();
        }
    });
    $('body').on('click',".department", function() {
        $('div').removeClass("active");
        $(this).addClass("active");
        var href = $(this).find('a').attr('href');
        var departmentId = href.substr(href.lastIndexOf("/") + 1);
        var groupsandsubs = "<div id=\"groupsandsubs\">";
        //"<div id=\"group\">Группы</div><div id=\"subdep\">Кафедры</div>"
        var request = $.ajax({
            url: '../get/groupname',
            type: "GET",
            async: false,
            dataType: 'json'
        });
        request.done ( function (data) {
            groupsandsubs+="<div id=\"group\"><a class=\"toframe\" href=\"../../group/view/" + departmentId +"\">" + data[0] + "</a></div>";
        });
        request.fail ( function () {
            groupsandsubs+="Server error";
        });
        var request = $.ajax({
            url: '../get/subdepname',
            type: "GET",
            async: false,
            dataType: 'json'
        });
        request.done ( function (data) {
            groupsandsubs+="<div id=\"subdep\"><a class=\"toframe\" href=\"../../subdepartment/view/" + departmentId +"\">" + data[0] + "</a></div>";
        });
        request.fail ( function () {
            groupsandsubs+="Server error";
        });
        groupsandsubs += "</div>";
        if (!$('#groupsandsubs').length) {
            $(this).after(groupsandsubs);
        } else if (!$(this).next('#groupsandsubs').length) {
            $('#groupsandsubs').remove();
            $(this).after(groupsandsubs);
        } else {
            $('#groupsandsubs').remove();
        }
    });
    $('body').on('click',"#group", function() {
        $('div').removeClass("active");
        $(this).addClass("active");
        var href = $(this).find('a').attr('href');
        var departmentId = href.substr(href.lastIndexOf("/") + 1);
        if (!$('#groups').length) {
            var request = $.ajax({
                url: '../get/groups/' + departmentId,
                type: "GET",
                dataType: 'json'
            });
            request.done ( function (data) {
                var groups="<div id=\"groups\">";
                for(var i=0;i<data.length;i++){
                    var obj = data[i];
                    groups+="<div class=\"group\"><a class=\"toframe\" href=\"../../student/view/group/" + obj.id + "\">" + obj.groupName + "</a></div>";
                    }
                groups+="</div>";
                $('#group').after(groups);
            });
            request.fail ( function () {
                $('#group').after("<div id=\"groups\">Server error</div>");
            });
        } else {
            $('#groups').remove();
        }
    });
    $('body').on('click',"#subdep", function() {
        $('div').removeClass("active");
        $(this).addClass("active");
        var href = $(this).find('a').attr('href');
        var departmentId = href.substr(href.lastIndexOf("/") + 1);
        if (!$('#subdeps').length) {
            var request = $.ajax({
                url: '../get/subdeps/' + departmentId,
                type: "GET",
                dataType: 'json'
            });
            request.done ( function (data) {
                var subdeps="<div id=\"subdeps\">";
                for(var i=0;i<data.length;i++){
                    var obj = data[i];
                    subdeps+="<div class=\"subdep\"><a class=\"toframe\" href=\"../../student/view/subdep/" + obj.id + "\">" + obj.subDepartmentName + "</a></div>";
                    }
                subdeps+="</div>";
                $('#subdep').after(subdeps);
            });
            request.fail ( function () {
                $('#subdep').after("<div id=\"subdeps\">Server error</div>");
            });
        } else {
            $('#subdeps').remove();
        }
    });
    $('body').on('click',".subdep", function() {
        $('div').removeClass("active");
        $(this).addClass("active");
        });
    $('body').on('click',".group", function() {
        $('div').removeClass("active");
        $(this).addClass("active");
    });
});
$('#contentframe').load(function() {
    var iframe=$(this)[0];
    var heightStr = $(iframe.contentWindow.document).height() + 'px';
    $(this).css({"height":heightStr});
});
