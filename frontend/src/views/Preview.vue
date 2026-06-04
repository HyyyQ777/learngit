<template>
  <div class="preview-page">
    <div class="page-container">
      <div class="card-container">
        <h2 class="section-title">报名信息预览</h2>
        <template v-if="application">
          <div v-if="application.status === 'draft'" style="text-align:center;margin-bottom:24px">
            <el-tag type="info" size="medium">草稿状态 - 待提交</el-tag>
          </div>
          <div v-if="application.status === 'pending'" style="text-align:center;margin-bottom:24px">
            <el-tag type="warning" size="medium">已提交 - 审核中</el-tag>
          </div>
          <div v-if="application.status === 'approved'" style="text-align:center;margin-bottom:24px">
            <el-tag type="success" size="medium">已通过</el-tag>
          </div>

          <div class="info-section">
            <h3>基本专业信息</h3>
            <el-row :gutter="24">
              <el-col :span="8" v-for="item in basicInfoItems" :key="item.label">
                <div class="info-item">
                  <span class="info-label">{{ item.label }}</span>
                  <span class="info-value">{{ item.value || '-' }}</span>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="info-section">
            <h3>详细个人信息</h3>
            <el-row :gutter="24">
              <el-col :span="8" v-for="item in detailInfoItems" :key="item.label">
                <div class="info-item">
                  <span class="info-label">{{ item.label }}</span>
                  <span class="info-value">{{ item.value || '-' }}</span>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="info-section">
            <h3>竞赛经历（近五年）</h3>
            <el-table :data="experiences" border style="width: 100%">
              <el-table-column prop="year" label="年份" width="100" />
              <el-table-column prop="competitionName" label="赛项/赛道名称" />
              <el-table-column prop="category" label="竞赛类别" width="120" />
              <el-table-column prop="position" label="担任职务" width="150" />
            </el-table>
            <p v-if="!experiences.length" style="color:#999;text-align:center;padding:20px">暂无竞赛经历</p>
          </div>

          <div class="info-section">
            <h3>获得的表彰或荣誉称号</h3>
            <div v-for="(h, i) in honors" :key="i" class="honor-item">
              <span class="honor-num">{{ i + 1 }}</span>
              {{ h.content || '-' }}
            </div>
          </div>

          <div class="form-actions" v-if="application.status === 'draft'">
            <el-button round @click="$router.push('/register')">返回修改</el-button>
            <el-button type="primary" round @click="handleSubmit">确认提交</el-button>
          </div>
          <div class="form-actions" v-if="application.status === 'pending'">
            <p style="color:#666;margin-bottom:12px">报名编号：{{ application.applicationNo }}，已提交审核，请耐心等待</p>
            <el-button type="primary" round @click="$router.push('/register')">修改报名信息</el-button>
          </div>
        </template>
        <div v-else style="text-align:center;padding:60px 0">
          <i class="el-icon-document" style="font-size:64px;color:#ccc"></i>
          <p style="color:#999;margin-top:16px">暂无报名记录</p>
          <el-button type="primary" round @click="$router.push('/register')" style="margin-top:16px">开始报名</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getMyApplication, submitApplication, getApplicationDetail } from '@/api/application'

export default {
  name: 'PreviewPage',
  data() {
    return {
      application: null,
      basicInfo: {},
      detailInfo: {},
      experiences: [],
      honors: []
    }
  },
  computed: {
    basicInfoItems() {
      const b = this.basicInfo
      return [
        { label: '申请赛道', value: b.track },
        { label: '性别', value: b.gender },
        { label: '民族', value: b.nationality },
        { label: '政治面貌', value: b.politicalStatus },
        { label: '学历/学位', value: b.education },
        { label: '身体状况', value: b.healthStatus },
        { label: '职称', value: b.title }
      ]
    },
    detailInfoItems() {
      const d = this.detailInfo
      return [
        { label: '姓名', value: d.name },
        { label: '出生年月', value: d.birthDate },
        { label: '手机号', value: d.phone },
        { label: '电子邮箱', value: d.email },
        { label: '工作单位', value: d.workUnit },
        { label: '工作年限', value: d.workYears },
        { label: '职务', value: d.position },
        { label: '擅长专业方向', value: d.specialty },
        { label: '身份证号', value: d.idCard }
      ]
    }
  },
  methods: {
    loadData() {
      getMyApplication().then(res => {
        if (res.data) {
          this.application = res.data
          try { this.basicInfo = JSON.parse(res.data.basicInfo) } catch { this.basicInfo = {} }
          try { this.detailInfo = JSON.parse(res.data.detailedInfo) } catch { this.detailInfo = {} }
          try { this.experiences = JSON.parse(res.data.competitionExperiences) || [] } catch { this.experiences = [] }
          try { this.honors = JSON.parse(res.data.honors) || [] } catch { this.honors = [] }
        }
      })
    },
    handleSubmit() {
      this.$confirm('确认提交报名信息？提交后将无法修改。', '确认提交', { type: 'warning' }).then(() => {
        submitApplication().then(res => {
          this.$message.success('提交成功！报名编号：' + res.data.applicationNo)
          this.loadData()
        })
      }).catch(() => {})
    }
  },
  mounted() {
    this.loadData()
  }
}
</script>

<style scoped>
.preview-page { padding: 24px 0 48px; }
.info-section {
  margin-bottom: 24px;
  padding: 20px;
  background: #fafafa;
  border-radius: 10px;
}
.info-section h3 {
  font-size: 15px;
  color: #1a237e;
  margin: 0 0 16px;
  padding-bottom: 8px;
  border-bottom: 1px dashed #e0e0e0;
}
.info-item {
  margin-bottom: 10px;
}
.info-label {
  font-size: 13px;
  color: #999;
  margin-right: 8px;
}
.info-value {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}
.honor-item {
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
  font-size: 14px;
  color: #333;
}
.honor-num {
  display: inline-block;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
  background: #1e88e5;
  color: #fff;
  border-radius: 50%;
  font-size: 12px;
  margin-right: 8px;
}
</style>
