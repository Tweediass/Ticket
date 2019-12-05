<template>
  <el-menu-item :index="menu.path || uniqueId" class="cust-submenu" width="190px"
                style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 190px;text-align: left">
    <template slot="title">
<!--    <i v-if="menu.icon111" :class="`fa fa-${menu.icon111}`" ></i>-->
<!--    <i v-if="menu.icon111 === undefined & !menu.iconSvg" class="fa fa-file-o"></i>-->
      <i class="el-icon-menu" style="color: #ffffff;width: 14px;"></i>
<!--    <d2-icon-svg v-if="menu.iconSvg" :name="menu.iconSvg"/>-->
    <span slot="title" style="color: #ffffff;">{{menu.title || '未命名菜单'}}</span>
    </template>
  </el-menu-item>
</template>

<script>
import { uniqueId } from 'lodash'
export default {
  name: 'd2-layout-header-aside-menu-item',
  props: {
    menu: {
      type: Object,
      required: false,
      default: () => {}
    }
  },
  data () {
    return {
      uniqueId: uniqueId('d2-menu-empty-')
    }
  }
}
</script>

<style>
  .cust-submenu >>> .el-submenu__title:hover {
    color: #ffffff;
    border: solid 1px #576ed6;
    background: #5ca0dd;
    background: -moz-linear-gradient(top, #3d75c8, #589cdb);
    background: -webkit-gradient(linear, left top, left bottom, from(#3d75c8), to(#589cdb));
  }

  .cust-submenu >>> .el-submenu__icon-arrow.el-icon-arrow-down::before {
    color: #ffffff;
  }
</style>