<template>
    <d2-container>
        <template slot="header">工单报表</template>

        <el-row>
            <el-col style="margin-top:15px;" :span="12">
                <br/><br/><br/>
                <p class="el-icon-s-data">&nbsp;个人工单处理情况报表</p>
                <d2-crud
                        :columns="columns"
                        :data="data"
                        :rowHandle="rowHandle"
                        @custom-emit-1="tkDetail">
                </d2-crud>
            </el-col>
            <!--        </div>-->

            <!--            <el-col :span="10">-->
            <!--                <p class="el-icon-s-data">&nbsp;工单处理情况分布竖状报表</p>-->
            <!--                <ve-histogram :data="chartData" :settings="chartSettings"-->

            <!--                ></ve-histogram>-->
            <!--            </el-col>-->
            <el-col style="margin-top:15px;" :offset="2" :span="10">
                <p class="el-icon-s-data">&nbsp;所有工单处理情况分布报表</p>
                <br/>
                <el-table
                        :data="tableData"
                        border>
                    <el-table-column
                            prop="New"
                            label="New">
                    </el-table-column>
                    <el-table-column
                            prop="Processing"
                            label="Processing">
                    </el-table-column>
                    <el-table-column
                            prop="Assigned"
                            label="Assigned">
                    </el-table-column>
                    <el-table-column
                            prop="Closed"
                            label="Closed">
                    </el-table-column>
                </el-table>
            </el-col>
            <el-col style="margin-top:15px;" :offset="2" :span="10">
                <br/><br/><br/>
                <p class="el-icon-s-data">&nbsp;所有工单处理情况分布报表(扇形)</p>
                <br/>
                <ve-pie :data="chartData"></ve-pie>
            </el-col>
        </el-row>
        <el-row>
            <el-col :offset="8" style="margin-left:333px;" :span="12">
                <el-pagination
                        :page-size="pageSize"
                        :total="total"
                        :current-page.sync="currentPage"
                        @current-change="show(currentPage)"
                        background
                        layout="prev, pager, next">
                </el-pagination>
            </el-col>
            <!--            <el-col :offset="2" :span="10">-->
            <!--                <p class="el-icon-s-data">&nbsp;工单处理情况分布扇形报表</p>-->
            <!--                <ve-pie :data="chartData"></ve-pie>-->
            <!--            </el-col>-->
        </el-row>
    </d2-container>
</template>

<script>

    import {getRequest} from "../../../utils/getRequest";

    export default {
        name: "reportDiagram",

        created() {
            // this.ageReport();
            // this.workReport();
            this.show(1);
            this.initCount();
            this.chartSettings = {
                dimension: ['工单状态'],
                yAxisName: ['数量']
            }
        },
        methods: {
            show(pageNo) {
                getRequest("/statistics/init?pageNo=" + pageNo).then(res => {
                    if (res.status === 200) {
                        this.total = res.data.extend.total;
                        this.data = Array.from(res.data.extend.statistics);
                    } else {
                        this.$message({
                            showClose: true,
                            message: '获取数据失败',
                            type: 'error'
                        });
                    }
                })
            },

            initCount() {
                getRequest("/statistics/initAll").then(
                    res => {
                        // this.data=Array.from(res.data.extend);
                        this.chartData.rows = [
                            {'工单状态': 'New', '数量': res.data.extend.New},
                            {'工单状态': 'Processing', '数量': res.data.extend.Processing},
                            {'工单状态': 'Assigned', '数量': res.data.extend.Assigned},
                            {'工单状态': 'Closed', '数量': res.data.extend.Closed},
                        ];
                        // this.tableData = res.data.extend;
                        this.tableData[0].New = res.data.extend.New;
                        this.tableData[0].Processing = res.data.extend.Processing;
                        this.tableData[0].Assigned = res.data.extend.Assigned;
                        this.tableData[0].Closed = res.data.extend.Closed;
                    }
                )
            },


            // ageReport() {
            //     getRequest("/report/ageReport").then(
            //         res => {
            //             this.data = Array.from(res.data);
            //             this.chartData.rows = [
            //                 {'年龄': '18-21岁', '人数': res.data[0]},
            //                 {'年龄': '22-25岁', '人数': res.data[1]},
            //                 {'年龄': '26-29岁', '人数': res.data[2]},
            //                 {'年龄': '30-33岁', '人数': res.data[3]},
            //                 {'年龄': '34-37岁', '人数': res.data[4]},
            //                 {'年龄': '38-41岁', '人数': res.data[5]},
            //                 {'年龄': '42岁以上', '人数': res.data[6]}
            //             ];
            //         }
            //     )
            // },
            // workReport() {
            //     getRequest("/report/workReport").then(
            //         res => {
            //             this.data = Array.from(res.data);
            //             this.chartData1.rows = [
            //                 {'出勤情况': '出勤', '出勤人次': res.data[0]},
            //                 {'出勤情况': '迟到', '出勤人次': res.data[1]},
            //                 {'出勤情况': '早退', '出勤人次': res.data[2]},
            //                 {'出勤情况': '旷工', '出勤人次': res.data[3]}
            //             ];
            //         }
            //     )
            // }
        },

        data() {
            return {
                pageNo: 1,
                pageSize: 5,
                total: 1,
                currentPage: 1,
                data: [],
                chartData: {
                    columns: ['工单状态', '数量'],
                    rows: [
                        {'工单状态': 'New', '数量': 1},
                        {'工单状态': 'Processing', '数量': 1},
                        {'工单状态': 'Assigned', '数量': 1},
                        {'工单状态': 'Closed', '数量': 1},
                    ]
                },
                dataRow: [],
                tableData: [{
                    New: '',
                    Processing: '',
                    Assigned: '',
                    Closed: '',
                }],
                // New: '',
                // Processing: '',
                // Assigned: '',
                // Closed: '',
                // chartData1: {
                //     columns: ['工单状态', '数量'],
                //     rows: [
                //         {'工单状态': 'New', '数量': 1},
                //         {'工单状态': 'Processing', '数量': 1},
                //         {'工单状态': 'Assigned', '数量': 1},
                //         {'工单状态': 'Closed', '数量': 1},
                //     ]
                // },
                //工单各列名
                columns: [
                    {
                        title: '操作员编号',
                        key: 'operatorId',
                    },
                    {
                        title: '操作员',
                        key: 'operatorName'
                    },
                    {
                        title: '处理工单数',
                        key: 'countProcessed'
                    },
                    {
                        title: '解决工单数',
                        key: 'countAssigned'
                    }
                ],
            }
        }
    }
</script>

<style lang="scss" scoped>
    .inner {
        position: absolute;
        top: 20px;
        right: 20px;
        bottom: 20px;
        left: 20px;
    }
</style>
