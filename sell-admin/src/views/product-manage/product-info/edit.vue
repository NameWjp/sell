<template>
  <div class="main-layout">
    <my-form ref="form" type="submit" :model="submitForm" :rules="submitRules">
      <my-form-item :span="22" label="商品名称" prop="productName">
        <el-input v-model="submitForm.productName" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品单价" prop="productPrice">
        <el-input v-model="submitForm.productPrice" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品类型" prop="categoryCode">
        <product-category-select v-model="submitForm.categoryCode" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品状态" prop="productStatus">
        <dict-select v-model="submitForm.productStatus" dict-code="product_status" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品库存" prop="productStock">
        <el-input v-model="submitForm.productStock" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品图标" prop="productIcon">
        <upload-img v-model="submitForm.productIcon" :disabled="isView" />
      </my-form-item>
      <my-form-item :span="22" label="商品描述" prop="productDescription">
        <el-input v-model="submitForm.productDescription" :disabled="isView" />
      </my-form-item>
      <template slot="control">
        <el-button @click="handleBack">返回</el-button>
        <loading-btn v-if="showControl" type="primary" @click="handleSubmit">保存</loading-btn>
      </template>
    </my-form>
  </div>
</template>

<script>
import { MyForm, MyFormItem } from '@/components/MyForm';
import LoadingBtn from '@/components/LoadingBtn';
import { toOnlyInteger, toNumber } from '@/utils/transform';
import { validatePositive, validateNotZero } from '@/utils/validator';
import { addProductInfo, editProductInfo, getProductInfoInfoById } from '@/api/product-info';
import DictSelect from '@/views/common/DictSelect';
import ProductCategorySelect from '@/views/common/ProductCategorySelect';
import UploadImg from '@/views/common/UploadImg';

export default {
  name: 'ProductInfoEdit',
  data() {
    return {
      type: this.$route.query.type,
      id: this.$route.query.id,
      submitForm: {
        productStatus: 1,
        productIcon: [],
      },
      submitRules: {
        productName: [
          { required: true, message: '商品名称不能为空' },
        ],
        productPrice: [
          { required: true, message: '商品单价不能为空' },
          { type: 'number', transform: toNumber, message: '商品单价只能为数字' },
          { validator: validatePositive },
          { validator: validateNotZero },
        ],
        categoryCode: [
          { required: true, message: '商品类型不能为空' },
        ],
        productStatus: [
          { required: true, message: '商品状态不能为空' },
        ],
        productStock: [
          { required: true, message: '商品库存不能为空' },
          { type: 'number', transform: toOnlyInteger, message: '商品库存只能为整数' },
          { validator: validatePositive },
          { validator: validateNotZero },
        ],
        productIcon: [
          { required: true, message: '商品图标不能为空' },
        ],
      },
    };
  },
  computed: {
    showControl() {
      return this.isAdd || this.isEdit;
    },
    isAdd() {
      return this.type === 'add';
    },
    isEdit() {
      return this.type === 'edit';
    },
    isView() {
      return this.type === 'view';
    },
  },
  mounted() {
    if (this.isEdit || this.isView) {
      this.getDetail();
    }
  },
  methods: {
    async getDetail() {
      const { data } = await getProductInfoInfoById({ id: this.id });
      const { productIcon, ...form } = data;
      if (productIcon) {
        form.productIcon = productIcon.split(',').map(url => ({ url }));
      }

      this.submitForm = {
        ...this.submitForm,
        ...form,
      };
    },
    handleBack() {
      this.$router.push({
        name: 'ProductInfo',
      });
    },
    async handleSubmit() {
      const valid = this.$refs.form.validate();
      if (!valid) return;

      let code;
      const { productIcon, ...data } = this.submitForm;
      if (productIcon && productIcon.length) {
        data.productIcon = productIcon.map(({ url }) => url).join(',');
      }

      if (this.isAdd) {
        ({ code } = await addProductInfo(data));
      }
      if (this.isEdit) {
        data.id = this.id;
        ({ code } = await editProductInfo(data));
      }

      if (code === 200) {
        this.$message.success(`${this.isAdd ? '新增' : this.isEdit ? '修改' : '操作'}成功`);
        this.handleBack();
      }
    },
  },
  components: {
    MyForm,
    MyFormItem,
    LoadingBtn,
    DictSelect,
    ProductCategorySelect,
    UploadImg,
  },
};
</script>

<style scoped lang="scss">
</style>
