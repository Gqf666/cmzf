<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid({
            url : "${pageContext.request.contextPath}/banner/selectByRowBounds",
            datatype : "json",
            colNames : [ 'ID', '标题', 'URL', '链接', '上传时间','描述', '状态' ],
            colModel : [
                {name : 'id',index : 'id',width : 55},
                {name : 'title',editable: true,width : 90},
                {name : 'url',editable: true,width : 100
                        ,formatter: function (cellvalue, options, rowObject) {
                        return "<img src='"+cellvalue+"' width='60px'/>";
                        },edittype:"file",editoptions:{enctype:"multipart/from-data"}
                },
                {name : 'href',editable: true,width : 80,align : "right"},
                {name : 'createDate',index : 'createDate',width : 80},
                {name : 'des',editable: true,width : 80,},
                {name : 'status',editable: true,width : 150,formatter:function (data) {
                        if(data == "1"){
                            return "展示";
                        }else{
                            return "冻结";
                        }
                    },edittype: "select",editoptions: {value:"1:展示;2:冻结"}}
                ],
            rowNum : 3, rowList : [ 10, 20, 30 ],
            pager : '#div1',
            sortname : 'id',
            mtype : "post",height:600,autowidth:true,
            styleUI : "Bootstrap",
            viewrecords : true,
            editurl:"${pageContext.request.contextPath}/banner/edit",
            sortorder : "desc",
            caption : "轮播图" });
    });
        /**
         * closeAfterXXX 完成操作后关闭窗口
         * */
        $("#bannerTable").jqGrid('navGrid', '#div1', {edit : true,add : true,del : true}
        ,{closeAfterEdit:true,
                        afterSubmit:function (response,PostData) {
                                var bannerId = response.responseJSON.bannerId;
                                $.ajaxFileUpload({
                                        url: "${pageContext.request.contextPath}/banner/upload",
                                        type:"post",
                                        datatype: "json",
                                        data:{bannerId:bannerId},
                                        fileElementId:"url",
                                        success:function (data) {
                                               alert(data)
                                                //刷新页面
                                                $("#bannerTable").trigger("reloadGrid");
                                        }
                                })
                                return postData;
                        }}
        ,{closeAfterAdd:true
        ,afterSubmit:function (response,PostData) {
            var bannerId = response.responseJSON.bannerId;
                $.ajaxFileUpload({
                    url: "${pageContext.request.contextPath}/banner/upload",
                    type:"post",
                    datatype: "json",
                    data:{bannerId:bannerId},
                    fileElementId:"url",
                    success:function (data) {
                        //刷新页面
                        $("#bannerTable").trigger("reloadGrid");
                    }
                })
                    return postData;
            }
        }
        );
</script>
<ul class="nav nav-tabs">
        <li><a href="${pageContext.request.contextPath}/banner/excelExport" id="out">导出轮播图Excel</a></li>
        <li><a href="javascript:void(0)" id="in">导入轮播图Excel</a></li>
        <li><a href="${pageContext.request.contextPath}/banner/outIn" id="outIn">导出轮播图Excel模版</a></li>
</ul>

        <table id="bannerTable">
        </table>
<div id="div1" style="height: 40px">

</div>