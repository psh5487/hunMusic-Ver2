$(document).ready(function() {

    $(".time td:nth-child(2)").click(function(){   //수업 시간대 클릭시 이벤트 toggle 호출(칸 색깔 변함)
        $(this).toggleClass("change");
    });

    $(".time td:nth-child(3)").click(function(){
        $(this).toggleClass("change");
    });

    $(".time td:nth-child(4)").click(function(){
        $(this).toggleClass("change");
    });

    $(".time td:nth-child(5)").click(function(){
        $(this).toggleClass("change");
    });

    $(".time td:nth-child(6)").click(function(){
        $(this).toggleClass("change");
    });

    $("#submit_button").click(function(){
        let checkedList = document.querySelectorAll(".change");

        // 휴학생, 아침러 있을 수 있음
//		// 시간표 미입력시 알림
//		if(checkedList.length === 0) {
//			alert("시간표를 입력하세요.");
//	        return;
//		}

        // 눌린 것들의 id 값을 배열에 넣어주기
        let classTimeArr = [];

        for(let i = 0; i < checkedList.length; i++) {
            let classTime = checkedList[i].id;
            classTimeArr.push(classTime);
        }

        // classTimeObj 객체에 classTime이 key, 만들어진 배열은 value 로 넣어주기
        let classTimeObj = {};
        classTimeObj.classTime = classTimeArr;

        // form에 담긴 이름 값 가져오기
        let name = $("#input_name").val();

        // 이름 미입력시 알림
        if (name == "") {
            alert("이름을 입력하세요.");
            $("#input_name").focus(); //입력포커스 이동
            return;                   //함수 종료
        }

        // 객체에 시간표 소유자 이름도 넣어주기
        classTimeObj.name = name;

        // 객체를 서블릿으로 넘기기
        jsonSend(classTimeObj);
    });
});

function jsonSend(obj) {
    let context = $('#contextPathHolder').attr('data-contextPath');

    $.ajax({
        url: context+"/addStudentTimeTable",
        type: 'POST',
        data: {sendString: JSON.stringify(obj)}, //http 통신은 string으로만 가능함
        dataType: 'text',                        //dataType은 서버로부터 받아오는 결과의 type을 의미

        success: function(data, response) { //data는 서버로부터 받은 값임
            alert("저장되었습니다!");
	        // console.log(data);
            // console.log(JSON.stringify(obj));
            window.location.replace(context+"/studentTimeTable"); //페이지 redirect 하기
        },
        error: function(request, status, error, data){
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error);
        }
    });
}