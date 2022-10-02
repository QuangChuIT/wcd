Window.Pagination = function () {
    let instance = this;
    instance.limit = 10;

    const extend = function (options) {
        instance.totalPages = options["totalPages"] || 0;
        instance.size = options["size"] || 0;
        instance.currentPage = options["number"] || 0;
        instance.currentPage++;
        instance.totalRecords = options["totalElement"] || 0;
        instance.first = options["first"];
        instance.last = options["last"];
        if (instance.currentPage - instance.step <= 0) {
            instance.from = 1;
        } else {
            instance.from = instance.currentPage - instance.step
        }

        if (instance.from + instance.step > instance.totalPages) {
            instance.to = instance.totalPages;
        } else {
            instance.to = instance.from + instance.step
        }
    }

    const init = (id, func, options) => {
        instance.id = id || "page";
        instance.func = func;
        extend(options);
        create();
        registerEvents();
    }

    const create = function () {
        if (instance.totalPages == 0) {
            if (instance.first == false && instance.last == false) {
                $('#' + instance.id).html('');
                $('#' + instance.id).hide();
                return;
            }
            if (instance.first == false || instance.last == false) {
                let html = '<div style="text-align: right;"><ul class="pagination pagination-sm" style="vertical-align: middle;padding-left: 10px;margin: 0;">';
                if (instance.first === false) {
                    html += '<li id="' + instance.id + '_go_prev"><a href="#"><i class="fa fa-backward"></i></a></li>';
                }
                if (instance.last == false) {
                    html += '<li id="' + instance.id + '_go_next"><a href="#"><i class="fa fa-forward"></i></a></li>';
                }
                html += '</ul></div>';
                $('#' + instance.id).html(html);
                $('#' + instance.id).show();
            } else {
                $('#' + instance.id).html('');
                $('#' + instance.id).hide();
            }

            return;
        }
        $('#' + instance.id).show();
        let html = '<div class="pull-left form-inline"><span>Hiển thị </span><lable class="select"><select id="' + instance.id + '_cblimit" class="form-control">';
        let limits = [10, 20, 50, 100];
        for (let i = 0; i < limits.length; i++) {
            if (limits[i] == instance.limit) {
                html += '<option value="' + limits[i] + '" selected>' + limits[i] + '</option>';
            } else {
                html += '<option value="' + limits[i] + '">' + limits[i] + '</option>';
            }
        }
        html += '</select></lable><span> bản ghi</span></div>';
        html += '<div class="pull-right"><div class="form-inline" style="display: inline-block;vertical-align: middle;"><span>Tổng số bản ghi ' + instance.totalRecords + '</span></div>'
            + '<ul class="pagination pagination-sm" style="vertical-align: middle;padding-left: 10px;margin: 0px;">';
        if (instance.first === false) {
            html += '<li id="' + instance.id + '_go_first"><a href="#"><i class="fa fa-fast-backward"></i></a></li>';
        }
        if (instance.currentPage > 1) {
            html += '<li id="' + instance.id + '_go_prev"><a href="#"><i class="fa fa-backward"></i></a></li>';
        }

        for (let i = instance.from; i <= instance.to; i++) {
            var page = i;
            if (i == instance.currentPage) {
                html += '<li class="active"><a href="#">' + page + '</a></li>';
            } else {
                html += '<li class="' + instance.id + '_go_page" data_page="' + page + '"><a href="#">' + page + '</a></li>';
            }
        }

        if (instance.currentPage >= 1 && instance.currentPage < instance.totalPages) {
            html += '<li id="' + instance.id + '_go_next"><a href="#"><i class="fa fa-forward"></i></a></li>';
        }
        if (instance.last == false) {
            html += '<li id="' + instance.id + '_go_last"><a href="#"><i class="fa fa-fast-forward"></i></a></li>';
        }
        html += '</ul></div>';
        $('#' + instance.id).html(html);
    }

    const registerEvents = function () {
        $('#' + instance.id + '_go_prev').on('click', goPrev);
        $('#' + instance.id + '_go_next').on('click', goNext);
        $('.' + instance.id + '_go_page').on('click', goPage);
        $('#' + instance.id + '_go_first').on('click', goFirst);
        $('#' + instance.id + '_go_last').on('click', goLast);
        $('#' + instance.id + '_cblimit').on('change', changeNumberLimit);
    }

    const goPrev = function () {
        if (instance.currentPage > 1) {
            instance.currentPage--;
        }
        instance.func();
    };

    const goNext = function () {
        instance.currentPage++;
        instance.func();
    };

    const goPage = function () {
        instance.currentPage = $(this).attr('data_page') || 1;
        instance.func();
    };

    const goFirst = function () {
        instance.currentPage = 1;
        instance.func();
    };

    const goLast = function () {
        instance.currentPage = instance.totalPages;
        instance.func();
    };

    const changeNumberLimit = function () {
        instance.limit = $('#' + instance.id + '_cblimit').val() || 20;
        instance.currentPage = 1;
        instance.func();
    };

    const reset = function () {
        instance.totalPages = 0;
        instance.size = 0;
        instance.currentPage = 1;
        instance.totalRecords = 0;
        instance.first = true;
        instance.last = true;
        instance.step = 5;
        instance.to = 0;
        instance.from = 0;
        instance.limit = 10;
        $('#' + instance.id).html('');
        $('#' + instance.id).hide();
    };

    return {
        size: function () {
            return instance.size;
        },
        totalPages: function () {
            return instance.totalPages;
        },
        currentPage: function () {
            return instance.currentPage;
        },
        limit: function () {
            return instance.limit;
        },
        init: init,
        reset: reset
    }
}