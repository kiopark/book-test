var index = {
  init : function () {
    var _this = this;
    $('btn-save').on('click', function () {
      _this.save();
    })
  },
  save : function () {
    var data = {
      title: $('#title').val(),
      author: $('#author').val(),
      content: $('#content').val()
    }

    $.ajax({
      type: 'POST',
      url: '/api/v1/posts',
      dataType: 'json',
      ContentType: 'application/json; charset=UTF-8',
      data: JSON.stringify(data)
    }).done(function () {
      alert('등록 완료!');
      window.location.href = "/";
    }).fail(function (error) {
      JSON.stringify(error);
    })
  }
}

index.init();
