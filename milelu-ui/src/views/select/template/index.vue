<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['select:template:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['select:template:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['select:template:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row>
      <el-col :span="4" v-for="(o, index) in 7" :key="o">
        <el-card :body-style="{ padding: '0px' }">
          <img src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
               class="image">
          <div style="padding: 14px;">
            <span>好吃的汉堡</span>
            <div class="bottom clearfix">
              <!--              <time class="time">{{ currentDate }}</time>-->
              <el-button type="text" class="button" @click="openPreview">预览</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改选版模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" size="medium" label-width="100px">
          <el-col :span="12">
            <el-row :gutter="15">
              <el-col :span="23">
                <el-form-item label="标题" prop="title">
                  <el-input v-model="form.title" placeholder="请输入标题" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="路由地址" prop="path">
                  <el-input v-model="form.path" placeholder="请输入路由地址" clearable :style="{width: '100%'}">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="类型" prop="type">
                  <el-select v-model="form.type" placeholder="请选择类型" clearable :style="{width: '100%'}">
                    <el-option label="H5" value="0"></el-option>
                    <el-option label="PDF" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="是否缓存" prop="isCache">
                  <el-input v-model="form.isCache" placeholder="请输入是否缓存" clearable
                            :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="价格" prop="price">
                  <el-input-number v-model="form.price" placeholder="价格" controls-position=right>
                  </el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="描述" prop="description">
                  <el-input v-model="form.description" type="textarea" placeholder="请输入描述"
                            :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="12">
            <el-row :gutter="15">
              <el-col :span="23">
                <el-form-item label="封面" prop="picture">
                  <input-picture ref="picture" :form="form" :data="field"/>
                </el-form-item>
              </el-col>
              <el-col :span="23">
                <el-form-item label="资源:" prop="resource">
                  <div>
                    <input type="file" ref="file" webkitdirectory name="file" @change.stop="changeData"/>
                    <p v-if="fileList" v-for="(item,index) in fileList" :key="index">
                      {{item.name}}
                    </p>
                    <span slot="footer" class="el-dialog__footer">
                      <el-button>取消</el-button>
                      <el-button @click="uploadRequest">确定</el-button>
                    </span>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="23">
            <el-row :gutter="15">

            </el-row>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <div id="qfy_template_preivew" class="pc-selected" v-if="preview">
      <div id="qfy_template_preivew_header"><span style="float:left;"> <span class="back" @click="returnView"
                                                                             style="position: relative;top:-6px;font-size:12px;"><svg
        style="position: relative;top:2px;margin-right:3px;stroke: #222;stroke-width: 7px;width: 14px;overflow: visible;-webkit-transform: scaleX(-1);transform: scaleX(-1);"
        width="8px" height="13px" viewBox="0 0 8 13" version="1.1"><g style="fill: #222;" id="Visual-Design"
                                                                      stroke="none"
                                                                      stroke-width="1" fill="none" fill-rule="evenodd"
                                                                      opacity="1"><g id="9b"
                                                                                     transform="translate(-344.000000, -357.000000)"
                                                                                     fill="#222222"><g id="templates"
                                                                                                       transform="translate(-202.000000, 165.000000)"><path
        d="M544,195 L544,203 L543,203 L543,195 L543,194 L552,194 L552,195 L544,195 Z" id="Combined-Shape"
        transform="translate(547.500000, 198.500000) rotate(-225.000000) translate(-547.500000, -198.500000) "></path></g></g></g></svg>返回 </span></span>
        <svg class="pc" style="margin-right:10px;margin-left:270px;cursor:pointer;padding:10px;" width="28" height="22"
             viewBox="0 0 28 22" xmlns="http://www.w3.org/2000/svg">
          <g fill="#000" fill-rule="evenodd">
            <path d="M11 18h1v4h-1z"></path>
            <path d="M9 21h10v1H9z"></path>
            <path d="M16 18h1v4h-1z"></path>
            <path
              d="M1 3v13c0 1.11.891 2 1.996 2h22.008A2.004 2.004 0 0 0 27 16V3c0-1.11-.891-2-1.996-2H2.996A2.004 2.004 0 0 0 1 3zM0 3c0-1.657 1.35-3 2.996-3h22.008A2.994 2.994 0 0 1 28 3v13c0 1.657-1.35 3-2.996 3H2.996A2.994 2.994 0 0 1 0 16V3z"></path>
          </g>
        </svg>
        <svg style="cursor:pointer;padding:10px;" class="mobile" width="12" height="22" viewBox="0 0 12 22"
             xmlns="http://www.w3.org/2000/svg">
          <g fill="#000" fill-rule="evenodd">
            <path
              d="M1 3.001V19C1 20.105 1.894 21 2.997 21h6.006A2 2 0 0 0 11 18.999V3A1.999 1.999 0 0 0 9.003 1H2.997A2 2 0 0 0 1 3.001zm-1 0A3 3 0 0 1 2.997 0h6.006A2.999 2.999 0 0 1 12 3.001V19A3 3 0 0 1 9.003 22H2.997A2.999 2.999 0 0 1 0 18.999V3z"></path>
            <path d="M5 18h2v2H5z"></path>
          </g>
        </svg>
      </div>
      <div id="qfy_template_preivew_content">
        <div class="iframe-inner">
          <div class="demo-mobile">
            <div class="demo-mobile-header">
              <div class="circle"></div>
              <div class="circle" style="width: 60px;"></div>
            </div>
            <div class="demo-mobile-bottom"></div>
          </div>
          <iframe :src="previewUrl"></iframe>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import InputPicture from '@/components/DynamicForms/FormComponent/InputPicture';
  import InputFile from '@/components/DynamicForms/FormComponent/InputFile';
  import {
    listTemplate,
    getTemplate,
    delTemplate,
    addTemplate,
    updateTemplate,
    uploadFiles,
    exportTemplate
  } from "@/api/select/template";

  export default {
    name: "Template",
    components: {InputPicture, InputFile},
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 选版模板表格数据
        templateList: [],
        fileList: [],
        // 弹出层标题
        title: "",
        field: {fieldAliase: "封面"},
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          title: null,
          path: null,
          isCache: null,
          type: null,
          picture: null,
          price: null,
          description: null,
        },
        previewUrl: 'https://www.baidu.com',
        preview: false,
        // 表单参数
        form: {},
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询选版模板列表 */
      getList() {
        this.loading = true;
        listTemplate(this.queryParams).then(response => {
          this.templateList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          categoryId: null,
          resourceId: null,
          title: null,
          path: null,
          isCache: null,
          type: null,
          picture: null,
          price: null,
          description: null,
          reserve1: null,
          reserve2: null
        };
        this.resetForm("form");
      },
      changeData() {
        console.log(this.$refs.file.files)
        this.fileList = this.$refs.file.files
        console.log(this.fileList)
      },
      uploadRequest(file) {
        let formData = new FormData();
        for (let i = 0; i < this.fileList.length; i++) {
          formData.append("files", this.fileList[i])
        }
        uploadFiles(formData).then(response => {
          if (response.code === 200) {
            file.onSuccess(response)
            this.msgSuccess("~上传成功~");
          } else {
            this.msgError(response.msg)
          }
        })
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加选版模板";
      },
      openPreview() {
        this.preview = true;
      },
      returnView(){
        this.preview = false;
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getTemplate(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改选版模板";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          let formData = new FormData();
          for (let i = 0; i < this.fileList.length; i++) {
            formData.append("files", this.fileList[i])
          }
          this.build(formData)
          if (valid) {
            if (this.form.id != null) {
              // updateTemplate(this.form).then(response => {
              //   this.msgSuccess("修改成功");
              //   this.open = false;
              //   this.getList();
              // });
            } else {
              console.log(this.form)
              //console.log(JSON.parse(this.$refs.picture.getFieldValue()));
              addTemplate(formData).then(response => {
                //   this.msgSuccess("新增成功");
                //   this.open = false;
                //   this.getList();
              })
            }
          }
        });
      },
      build(formData) {
        formData.append("path", this.form.path);
        formData.append("title", this.form.title);
        formData.append("isCache", this.form.isCache);
        formData.append("type", this.form.type);
        formData.append("picture", JSON.stringify(this.$refs.picture.getFieldValue()));
        formData.append("price", this.form.price);
        formData.append("description", this.form.description);
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除选版模板编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delTemplate(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有选版模板数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportTemplate(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>
<style>
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  #qfy_template_preivew {
    position: fixed;
    top: 0px;
    right: 0px;
    left: 0px;
    bottom: 0px;
    z-index: 4456456;
    text-align: center;
    font-family: 微软雅黑;
    background: rgb(245, 245, 245);
  }

  #qfy_template_preivew_header {
    height: 100px;
    line-height: 100px;
    margin: 0 5%;
    color: #333;
  }

  #qfy_template_preivew_content {
    height: 100%;
  }

  #qfy_template_preivew_content .iframe-inner {
    position: relative;
    margin: 0 auto;
    height: 100%;
  }

  #qfy_template_preivew .demo-mobile {
    -webkit-transform: translateX(-50%);
    transform: translateX(-50%);
    left: 50%;
    background: #fff;
    z-index: -1;
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    -webkit-box-shadow: 0 0 100px rgb(0 0 0 / 7%);
    box-shadow: 0 0 100px rgb(0 0 0 / 7%);
    border-radius: 30px;
    background: #fff;
  }

  #qfy_template_preivew .demo-mobile-header {
    width: 89px;
    height: 80px;
    margin: 0 auto;
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
  }

  #qfy_template_preivew .demo-mobile-bottom {
    position: absolute;
    display: inline-block;
    border: 1px solid #e5e5e5;
    border-radius: 3px;
    bottom: 20px;
    -webkit-transform: translateX(-50%);
    transform: translateX(-50%);
    left: 50%;
    height: 45px;
    width: 45px;
    border-radius: 100%;
  }

  #qfy_template_preivew.pc-selected #qfy_template_preivew_content iframe {
    height: calc(100% - 100px);
  }

  #qfy_template_preivew_content iframe {
    border: 0;
    width: 100%;
    height: 100%;
    border: 1px solid #eee;
    position: relative;
    z-index: 2;
  }
</style>
