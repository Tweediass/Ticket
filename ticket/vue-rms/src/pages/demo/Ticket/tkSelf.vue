<template>
    <d2-container>
        <template slot="header">
            <div class="search-box">
                <el-row style="margin-top: 10px; margin-bottom: 10px;">
                    <el-col :span="6">
                        <el-input
                                placeholder="请输入工单编号..."
                                prefix-icon="el-icon-search"
                                style="width: 97%"
                                v-model="id">
                        </el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-input
                                placeholder="请输入发布人名..."
                                prefix-icon="el-icon-search"
                                style="width: 97%"
                                v-model="username">
                        </el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-input
                                placeholder="请输入操作员名..."
                                prefix-icon="el-icon-search"
                                style="width: 97%"
                                v-model="operatorName">
                        </el-input>
                    </el-col>
                    <el-col :span="6" align="center">
                        <el-tooltip class="item" effect="light" content="查询" placement="top"></el-tooltip>
                        <el-button style="width: 70%"
                                   class="myButtonQ"
                                   size="mini"
                                   type="primary"
                                   @click="findTicket"
                                   icon="el-icon-search">
                            查询
                        </el-button>
                    </el-col>
                </el-row>
                <el-row style="margin-bottom: 0px;">
                    <el-col :span="6">
                        <el-select class="sixLineInput" v-model="server" filterable style="width: 97%"
                                   placeholder="请选择服务分类">
                            <el-option
                                    v-for="item in servers"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6">
                        <el-select class="sixLineInput" filterable style="width: 97%"
                                   placeholder="请选择问题类型" v-model="questionType">
                            <el-option
                                    v-for="item in questiontypes"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6">
                        <el-select class="sixLineInput" filterable style="width: 97%"
                                   placeholder="请选择工单状态" v-model="status">
                            <el-option
                                    v-for="item in statuses"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="6" align="center">
                        <el-tooltip class="item" effect="light" content="刷新" placement="top"></el-tooltip>
                        <el-button style="width: 70%"
                                   class="myButtonR"
                                   size="mini"
                                   type="success"
                                   @click="refresh"
                                   icon="el-icon-refresh">
                            刷新
                        </el-button>
                    </el-col>

                </el-row>
            </div>
        </template>
        <!--打表-->
        <div class="myTable" style="margin-top:15px;">
            <el-table
                    :data="tickets"
                    v-loading="tableLoading"
                    border

                    :row-style="tableRowClassName"
                    :header-cell-style="{background:'#f0f0f0', color:'#68727c'}">
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
                                     :to="{name:'demo-tkDetail', query:{id: scope.row.id, backroute: '/demo/tkSelf'}}">
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
                    <template slot-scope="scope" >
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
                    <template slot-scope="scope" >
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
            <!--            </el-col>-->
        </div>
        <el-col :offset="8" style="margin-left:600px;">
            <el-pagination
                    :page-size="pageSize"
                    :total="total"
                    :current-page.sync="currentPage"
                    @current-change="show(currentPage)"
                    background
                    layout="total, prev, pager, next, jumper">
            </el-pagination>
        </el-col>
    </d2-container>
</template>

<script>
    import qs from "qs";
    import {getRequest} from "../../../utils/getRequest";
    import {postRequest} from "../../../utils/postRequest";
    import {putRequest} from "../../../utils/putRequest";
    import {deleteRequest} from "../../../utils/deleteRequset";

    export default {
        name: "tkSelf",

        created() {
            this.show(1);
        },

        filters: {
            statusFilter(status) {
                const statusMap = {
                    "New": 'danger',
                    "Assigned": 'success',
                    "Processing": '',
                    "Closed": 'warning'
                };
                return statusMap[status]
            }
        },

        methods: {
            show(pageNo) {
                getRequest("/ticket/findSelf?id=" + this.id.trim() +
                    "&username=" + this.username.trim() +
                    "&server=" + this.server.trim() +
                    "&questionType=" + this.questionType.trim() +
                    "&operatorName=" + this.operatorName.trim() +
                    "&status=" + this.status.trim() +
                    "&pageNo=" + pageNo
                ).then(res => {
                    if (res.status === 200) {
                        this.total = res.data.extend.total;
                        // this.data = Array.from(res.data.extend.ticket);
                        this.tickets = res.data.extend.ticket;
                        this.currentPage = pageNo;
                    } else {
                        this.$message({
                            showClose: true,
                            message: '获取数据失败',
                            type: 'error'
                        });
                    }
                })
            },

            //查找工单
            findTicket() {
                this.show(1);
                this.currentPage = 1;
            },

            //刷新
            refresh() {
                this.id = "";
                this.username = "";
                this.server = "";
                this.questionType = "";
                this.operatorName = "";
                this.status = "";
                this.show(1);
                // getRequest("/ticket/findSelf").then(res => {
                //     if (res.status === 200) {
                //         this.total = res.data.extend.total;
                //         this.data = Array.from(res.data.extend.ticket);
                //         this.currentPage = 1;
                //     } else {
                //         this.$message({
                //             showClose: true,
                //             message: '获取数据失败',
                //             type: 'error'
                //         });
                //     }
                // })
            },

            // tkDetail({row}) {
            //     this.$router.push({name: 'demo-tkDetail', query: {id: row.id, backroute: '/demo/tkSelf'}})
            // },
            tableRowClassName({row, rowIndex}) {
                if (rowIndex%2 === 1) {
                    return 'background: #f0ffff;';
                }
                return '';
            }
        },

        data() {
            return {
                id: '',
                username: '',
                server: '',
                questionType: '',
                operatorName: '',
                status: '',
                pageNo: 1,
                pageSize: 15,
                total: 1,
                currentPage: 1,
                tableLoading: false,
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

                //下拉选择
                //工单状态选项
                statuses: [
                    {value: 'New', label: 'New(未处理)'},
                    {value: 'Assigned', label: 'Assigned(已解决)'},
                    {value: 'Processing', label: 'Processing(处理中)'},
                    {value: 'Closed', label: 'Closed(已关闭)'}
                ],

                //工单问题类型选项
                questiontypes: [{
                    value: 'PC问题',
                    label: 'PC问题'
                }, {
                    value: '办公软件',
                    label: '办公软件'
                }, {
                    value: '邮件问题',
                    label: '邮件问题'
                }, {
                    value: '账户问题',
                    label: '账户问题'
                }, {
                    value: '软件问题',
                    label: '软件问题'
                }, {
                    value: '网络问题',
                    label: '网络问题'
                }, {
                    value: '硬件问题',
                    label: '硬件问题'
                }, {
                    value: '移动设备问题',
                    label: '移动设备问题'
                }],

                //工单服务分类选项
                servers: [{
                    value: '咨询请求',
                    label: '咨询请求'
                }, {
                    value: '服务请求',
                    label: '服务请求'
                }, {
                    value: '事件申报',
                    label: '事件申报'
                }, {
                    value: '其他',
                    label: '其他'
                }],
                value: '',

                // //工单各列名
                // columns: [
                //     {
                //         title: '工单编号',
                //         key: 'tkId',
                //     },
                //     {
                //         title: '发布人',
                //         key: 'customerName'
                //     },
                //     {
                //         title: '工单标题',
                //         key: 'title'
                //     },
                //     {
                //         title: '服务分类',
                //         key: 'server'
                //     },
                //     {
                //         title: '问题类型',
                //         key: 'questionType'
                //     },
                //     {
                //         title: '创建日期',
                //         key: 'createDate'
                //     },
                //     {
                //         title: '操作员',
                //         key: 'operatorName'
                //     },
                //     {
                //         title: '工单状态',
                //         key: 'status'
                //     }
                // ],
                //
                // data: [],
                //
                // //每列的修改删除按钮
                // rowHandle: {
                //     custom: [
                //         {
                //             text: '详情',
                //             type: 'danger',
                //             size: 'small',
                //             emit: 'custom-emit-1',
                //         }
                //     ]
                // },
            }
        }
    }
</script>

<style scoped>
    .search-box {
        margin-bottom: 10px;
        border-radius: 4px;
        padding: 1px 5px 10px 5px;
        box-sizing: border-box;
        background-color: #e5edf5;
    }

    .myButtonQ >>> .el-button--primary {
        color: #fff;
        font-size: 14px;
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ {
        font-size: 14px;
        background-color: #3771de;
        border-color: #3771de;
    }

    .myButtonQ:hover {
        font-size: 14px;
        background-color: #5598de;
        border-color: #5598de;
    }

    .myButtonR {
        font-size: 14px;
    }

    .el-table .warning-row {
        background: oldlace;
    }

    .el-table .success-row {
        background: #f0f9eb;
    }
</style>
