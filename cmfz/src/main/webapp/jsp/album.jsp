<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
    <script type="text/javascript">
        $(function () {
            $("#ftable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/album/selectByRows",
                    datatype : "json",
                    height : 500,
                    colNames : [ 'ID', '标题','评分','作者','播音','章节数量','内容简介','封面','发布日期','状态'],
                    colModel : [
                        {name : 'id',index : 'id',  width : 55},
                        {name : 'title',editable: true,width : 90},
                        {name : 'score',editable: true,index : 'invdate',width : 90},
                        {name : 'author',editable: true,width : 90},
                        {name : 'broadcast',editable: true,width : 90},
                        {name : 'count',editable: true,index : 'invdate',width : 90},
                        {name : 'desc',editable: true,width : 90},
                        {name : 'cover',editable: true,formatter: function (cellvalue, options, rowObject) {
                                return "<img src='${pageContext.request.contextPath}/upload/img/"+cellvalue+"' width='60px'/>";
                            }, edittype:"file",editoptions:{enctype:"multipart/from-data"},width : 90},
                        {name : 'createDate',index : 'createDate',width : 90,edittype:"data"},
                        {name : 'status',editable: true,width : 90,formatter:function (data) {
                                if(data=="1"){
                                    return "展示"
                                }else{
                                    return "冻结"
                                }
                            },edittype: "select",editoptions: {value:"1:展示;2:冻结"}
                        }
                    ],
                    rowNum : 8,
                    rowList : [ 8, 10, 20, 30 ],
                    pager : '#fpage',
                    sortname : 'id',
                    styleUI:"Bootstrap",
                    mtype:"post",
                    viewrecords : true,
                    sortorder : "desc",
                    multiselect : false,
                    //开启子表格支持
                    subGrid : true,
                    caption : "Grid as Subgrid",
                    editurl:"${pageContext.request.contextPath}/album/edit",
                    //subgrid_id：父级行的Id, row_id:当前数据的Id
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        //调用生产子表格的方法
                        addSubGrid(subgrid_id,row_id);

                    },
                    //删除表格的方法
                    subGridRowColapsed : function(subgrid_id, row_id) {
                        // this function is called before removing the data
                        //var subgrid_table_id;
                        //subgrid_table_id = subgrid_id+"_t";
                        //jQuery("#"+subgrid_table_id).remove();
                    }
                });
             //生成表格 生产子表格工具栏
            $("#ftable").jqGrid('navGrid', '#fpage', {
                edit : true,
                add : true,
                del : true
            },{closeAfterEdit:true,
                afterSubmit:function (response,postData) {
                var albumId = response.responseJSON.albumId;
                $.ajaxFileUpload({
                    url: "${pageContext.request.contextPath}/album/upload",
                    type:"post",
                    datatype: "json",
                    data:{albumId:albumId},
                    fileElementId:"cover",
                    success:function (data) {
                        //刷新页面
                        $("#ftable").trigger("reloadGrid");
                    }
                })
                return postData;
            }}
            ,{closeAfterAdd: true,afterSubmit:function (response,postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        type:"post",
                        datatype: "json",
                        data:{albumId:albumId},
                        fileElementId:"cover",//传递的参数
                        success:function (data) {
                            //刷新页面
                            $("#ftable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }}
            );
        })
        //subgrid_id 行Id ，row_id 数据ID
        function addSubGrid(subgrid_id,row_id){
            var subgrid_table_id, pager_id;
            subgrid_table_id = subgrid_id + "_t";
            pager_id = "p_" + subgrid_table_id;
            $("#" + subgrid_id).html(
                "<table id='" + subgrid_table_id
                + "' class='scroll'></table><div id='"
                + pager_id + "' class='scroll'></div>");
            $("#" + subgrid_table_id).jqGrid(
                {
                    url : "${pageContext.request.contextPath}/chapter/selectByRows?albumId="+row_id,
                    datatype : "json",
                    colNames : [ 'id', '标题', '大小','时长','上传时间','操作'],
                    colModel : [
                        {name : "id",  align:"center",hidden:true},
                        {name : "title",align:"center",editable:true},
                        {name : "size",align:"center"},
                        {name : "time",align:"center"},
                        {name : "createTime",index : "qty",width : 70,align : "right"},
                        {name : "url",formatter:function (cellvalue,options,rowObject) {
                            var button="<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>";
                            button+="<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        },editable: true,edittype:"file",editoptions:{enctype:"multipart/from-data"}}
                    ],
                    rowNum : 20,
                    pager : pager_id,
                    sortname : 'num',
                    sortorder : "asc",
                    height : '100%',
                    styleUI: "Bootstrap",
                    autowidth:true,
                    editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+row_id
                });
            $("#" + subgrid_table_id).jqGrid('navGrid',
                "#" + pager_id, {
                    edit : true,
                    add : true,
                    del : true
                },{closeAfterEdit:true,
                    afterSubmit:function (response,postData) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            type:"post",
                            datatype: "json",
                            data:{chapterId:chapterId},
                            fileElementId:"url",
                            success:function (data) {
                                //刷新页面
                                $("#" + subgrid_table_id).trigger("reloadGrid");
                            }
                        })
                        return postData;
                    }},{closeAfterAdd:true,
                    afterSubmit:function (response,postData) {
                        var chapterId = response.responseJSON.chapterId;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            type:"post",
                            datatype: "json",
                            data:{chapterId:chapterId},
                            fileElementId:"url",
                            success:function (data) {
                                //刷新页面
                                $("#" + subgrid_table_id).trigger("reloadGrid");
                            }
                        })
                        return postData;
                    }}
                );
        }
        function onPlay(cellValue) {
            $("#music").attr("src",cellValue);
            $("#myModal").modal("show");
        }
        function download(cellValue) {
            location.href="${pageContext.request.contextPath}/chapter/download?url="+cellValue;
        }
    </script>

    <table id="ftable">
        <div id="fpage" style="height: 50px"></div>
    </table>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div><!-- /.modal -->
</div>

