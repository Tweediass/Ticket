<template>
    <div class="table">
        <button @click="exportExcel">点击导出</button>
        <!--        <el-table-->
        <!--                :data="tableData"-->
        <!--                border-->
        <!--                style="width: 100%"-->
        <!--                id="out-table"-->
        <!--        >-->
        <!--            <el-table-column-->
        <!--                    prop="date"-->
        <!--                    label="日期"-->
        <!--                    width="180"-->
        <!--            >-->
        <!--            </el-table-column>-->
        <!--            <el-table-column-->
        <!--                    prop="name"-->
        <!--                    label="姓名"-->
        <!--                    width="180"-->
        <!--            >-->
        <!--            </el-table-column>-->
        <!--            <el-table-column-->
        <!--                    prop="address"-->
        <!--                    label="地址"-->
        <!--            >-->
        <!--            </el-table-column>-->
        <!--        </el-table>-->
        <!--            <div style="margin-left: 5px;margin-right: 20px;display: inline" align="right">-->
        <!--            <el-button type="success" size="mini" @click="exportEmps" ><i class="fa fa-lg fa-level-down"-->
        <!--                                                                         style="margin-right: 5px"></i>导出数据-->
        <!--            </el-button>-->
        <!--                </div>-->
        <el-table
                :data="tickets"
                v-loading="tableLoading"
                border
                :row-style="tableRowClassName"
                :header-cell-style="{background:'#f0f0f0', color:'#68727c'}"
                id="out-table">
            <!--            <el-table-column
              type="selection"
              width="30"
              align="center">
            </el-table-column>-->
            <el-table-column
                    prop="rowNum"
                    label="序号"
                    fixed="left"
                    width="50"
                    align="center"
                    show-overflow-tooltip="true">
                <!--                    <template slot-scope="scope">-->
                <!--                        {{scope.$index}}-->
                <!--                    </template>-->
            </el-table-column>
            <el-table-column
                    prop="tkId"
                    fixed="left"
                    label="工单编号"
                    width="220"
                    align="center"
                    show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <router-link style="color:#0b6dd8;text-decoration:none;"
                                 :to="{name:'demo-tkDetail', query:{id: scope.row.id, backroute: '/demo/tkAll'}}">
                        {{scope.row.tkId}}
                    </router-link>
                </template>
            </el-table-column>
            <el-table-column
                    prop="createDate"
                    width="220"
                    label="创建日期"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="工单标题"
                    width="150"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    prop="customerName"
                    label="发布人"
                    width="120"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    prop="userId"
                    label="发布人编号"
                    width="120"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="发布人手机"
                    width="120"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="发布人邮箱"
                    width="220"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    width="100"
                    prop="server"
                    label="服务分类"
                    align="center"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    width="150"
                    prop="questionType"
                    align="center"
                    label="问题分类"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    width="150"
                    prop="operatorName"
                    align="center"
                    label="操作员"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    width="100"
                    prop="operatorId"
                    align="center"
                    label="操作员编号"
                    show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <div v-if="scope.row.operatorId">
                        {{scope.row.operatorId}}
                    </div>
                    <div v-else>
                        {{'暂无'}}
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                    width="300"
                    prop="reply"
                    align="center"
                    label="回复"
                    show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <div v-if="scope.row.reply">
                        {{scope.row.reply}}
                    </div>
                    <div v-else>
                        {{'暂无'}}
                    </div>
                </template>
            </el-table-column>
            <el-table-column
                    width="100"
                    prop="evaluation"
                    align="center"
                    label="评分"
                    show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
                    width="200"
                    prop="opinion"
                    align="center"
                    label="意见"
                    show-overflow-tooltip="true">
            </el-table-column>

            <!--              <el-table-column-->
            <!--                prop="logId"-->
            <!--                fixed="left"-->
            <!--                label="工单编号"-->
            <!--                width="140"-->
            <!--                align="center"-->
            <!--                show-overflow-tooltip="true">-->
            <!--                <template slot-scope="scope">-->
            <!--                  <router-link style="color:#0b6dd8;text-decoration:none;"-->
            <!--                               :to="{path:'/tkt/detail', query:{index: scope.$index, row: scope.row, backroute: '/tkt/all'}}">-->
            <!--                    {{scope.row.logId}}-->
            <!--                  </router-link>-->
            <!--                </template>-->
            <!--              </el-table-column>-->

            <el-table-column
                    fixed="right"
                    prop="status"
                    label="工单状态"
                    width="110"
                    align="center"
                    show-overflow-tooltip="true">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status }}</el-tag>
                    <!--                                <el-tooltip class="item" effect="light" content="详情" placement="top"></el-tooltip>-->
                    <!--                                <el-tag  type="danger" align="right"-->
                    <!--                                        v-on:click.native="showEditTicketView(scope.$index, scope.row)">-->
                    <!--                                    <i class="el-icon-edit-outline">{{"详情"}}</i>-->
                    <!--                                </el-tag>-->
                </template>
            </el-table-column>
        </el-table>
        <!--给按钮绑定事件-->

    </div>
</template>

<script>
    // 引入导出Excel表格依赖
    import FileSaver from "file-saver";
    import XLSX from "xlsx";
    import {getRequest} from "../../../utils/getRequest";

    export default {
        name: "javascriptthree",
        data() {
            return {
                tickets: [{
                    rowNum: '',
                    id: '',
                    tkId: '',
                    userId: '',
                    phone: '',
                    email: '',
                    createDate: '',
                    server: '',
                    questionType: '',
                    operatorId: '',
                    status: '',
                    evaluation: '',
                    customerName: '',
                    operatorName: '',
                    opinion: '',
                    reply: ''
                }],
                tableData: [
                    {
                        date: "2016-05-02",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1518 弄"
                    },
                    {
                        date: "2016-05-04",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1517 弄"
                    },
                    {
                        date: "2016-05-01",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1519 弄"
                    },
                    {
                        date: "2016-05-03",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1516 弄"
                    }
                ]
            };
        },
        mounted() {
        },

        created() {
            this.show(1);
            // this.$message.warning('!' + this.tickets.toString());
        },
        methods: {
            //定义导出Excel表格事件
            exportExcel() {
                //     /* 从表生成工作簿对象 */
                //     var wb = XLSX.utils.table_to_book(document.querySelector("#out-table"));
                //     /* 获取二进制字符串作为输出 */
                //     var wbout = XLSX.write(wb, {
                //         bookType: "xlsx",
                //         bookSST: true,
                //         type: "array"
                //     });
                //     try {
                //         FileSaver.saveAs(
                //             //Blob 对象表示一个不可变、原始数据的类文件对象。
                //             //Blob 表示的不一定是JavaScript原生格式的数据。
                //             //File 接口基于Blob，继承了 blob 的功能并将其扩展使其支持用户系统上的文件。
                //             //返回一个新创建的 Blob 对象，其内容由参数中给定的数组串联组成。
                //             new Blob([wbout], {type: "application/octet-stream"}),
                //             //设置导出文件名称
                //             "sheetjs.xlsx"
                //         );
                //     } catch (e) {
                //         if (typeof console !== "undefined") console.log(e, wbout);
                //     }
                //     return wbout;
                /* generate workbook object from table */
                let table = document.querySelector("#out-table").cloneNode(true);
                // 因为element-ui的表格的fixed属性导致多出一个table，会下载重复内容，这里删除掉
                table.removeChild(table.querySelector(".el-table__fixed"));
                table.removeChild(table.querySelector(".el-table__fixed-right"));
                let wb = XLSX.utils.table_to_book(table, {raw: true});//mytable为表格的id名
                /* get binary string as output */
                let excName = this.companyReport;
                if (this.dataEndDate) {
                    excName += '(' + this.dataEndDate + ')'
                }
                let wbout = XLSX.write(wb, {
                    bookType: "xlsx",
                    bookSST: true,
                    type: "array",
                });
                try {
                    FileSaver.saveAs(
                        new Blob([wbout], {type: "application/octet-stream"}),
                        excName + ".xlsx"
                    );
                } catch (e) {
                    if (typeof console !== "undefined") console.log(e, wbout);
                }
                return wbout;
            },

            show(pageNo) {
                this.tableLoading = true;
                getRequest("/ticket/find?pageNo=" + pageNo +
                    "&pageSize=" + 100
                ).then(res => {
                    this.tableLoading = false;
                    if (res.status === 200) {
                        this.total = res.data.extend.total;
                        // this.data = Array.from(res.data.extend.ticket);
                        this.tickets = res.data.extend.ticket;
                        this.currentPage = pageNo;
                        // this.$message.warning('1**'+this.tickets.operatorId);
                        // this.$message.warning('1**'+res.data.extend.ticket.operatorId);
                        // if (!this.tickets.operatorId) {
                        //     this.tickets.operatorId = '暂无';
                        // }
                        // this.$message.warning('2**'+this.tickets.operatorId);
                    } else {
                        this.$message({
                            showClose: true,
                            message: '获取数据失败',
                            type: 'error'
                        });
                    }
                })
            },
        }
    };
</script>

<style scoped>
    .table {
        width: 100%;
        height: 300px;
    }
</style>