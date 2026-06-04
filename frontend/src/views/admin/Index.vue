<template>
  <div class="admin-page">
    <div class="page-container">
      <div class="card-container">
        <div class="admin-header">
          <h2 class="section-title">教练管理端 - 报名情况总览</h2>
          <el-button type="primary" size="small" icon="el-icon-refresh" @click="loadData" round>刷新数据</el-button>
        </div>

        <el-row :gutter="16" class="stat-row">
          <el-col :span="6">
            <div class="stat-card">
              <div class="stat-num">{{ totalCount }}</div>
              <div class="stat-label">总报名数</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card warning">
              <div class="stat-num">{{ pendingCount }}</div>
              <div class="stat-label">待审核</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card success">
              <div class="stat-num">{{ approvedCount }}</div>
              <div class="stat-label">已通过</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card danger">
              <div class="stat-num">{{ rejectedCount }}</div>
              <div class="stat-label">已退回</div>
            </div>
          </el-col>
        </el-row>

        <div class="filter-bar">
          <el-input v-model="keyword" placeholder="搜索姓名/单位" prefix-icon="el-icon-search" clearable size="small" style="width: 220px" @change="loadData" />
          <el-select v-model="statusFilter" placeholder="状态筛选" clearable size="small" style="width: 140px; margin-left: 12px" @change="loadData">
            <el-option label="全部" value="all" />
            <el-option label="草稿" value="draft" />
            <el-option label="待审核" value="pending" />
            <el-option label="审核中" value="reviewing" />
            <el-option label="已通过" value="approved" />
            <el-option label="已退回" value="rejected" />
          </el-select>
        </div>

        <el-table :data="applications" v-loading="loading" border stripe style="width: 100%; margin-top: 16px">
          <el-table-column prop="id" label="序号" width="70" />
          <el-table-column prop="applicationNo" label="报名编号" width="180" />
          <el-table-column label="姓名" min-width="100">
            <template slot-scope="scope">
              {{ getDetailField(scope.row, 'name') }}
            </template>
          </el-table-column>
          <el-table-column label="工作单位" min-width="180">
            <template slot-scope="scope">
              {{ getDetailField(scope.row, 'workUnit') }}
            </template>
          </el-table-column>
          <el-table-column label="申请赛道" min-width="140">
            <template slot-scope="scope">
              {{ getBasicField(scope.row, 'track') }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" width="130">
            <template slot-scope="scope">
              {{ getDetailField(scope.row, 'phone') }}
            </template>
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template slot-scope="scope">
              <span :class="['status-tag', 'status-' + scope.row.status]">{{ statusLabel(scope.row.status) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="提交时间" width="170">
            <template slot-scope="scope">
              {{ scope.row.submittedAt || '-' }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160" fixed="right">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showDetail(scope.row)">详情</el-button>
              <el-button v-if="scope.row.status === 'pending'" type="text" size="small" style="color:#4caf50" @click="doAudit(scope.row, 'approved')">通过</el-button>
              <el-button v-if="scope.row.status === 'pending'" type="text" size="small" style="color:#f44336" @click="doAudit(scope.row, 'rejected')">退回</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div style="text-align:center;margin-top:16px">
          <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="loadData" />
        </div>
      </div>
    </div>

    <el-dialog title="报名详情" :visible.sync="detailVisible" width="800px" top="5vh">
      <div v-if="detailRow">
        <div class="info-section"><h3>基本专业信息</h3>
          <el-row :gutter="16">
            <el-col :span="8" v-for="item in detailBasicItems" :key="item.label">
              <div class="info-item"><span class="info-label">{{ item.label }}</span><span class="info-value">{{ item.value || '-' }}</span></div>
            </el-col>
          </el-row>
        </div>
        <div class="info-section"><h3>详细个人信息</h3>
          <el-row :gutter="16">
            <el-col :span="8" v-for="item in detailInfoData" :key="item.label">
              <div class="info-item"><span class="info-label">{{ item.label }}</span><span class="info-value">{{ item.value || '-' }}</span></div>
            </el-col>
          </el-row>
        </div>
        <div class="info-section"><h3>竞赛经历</h3>
          <el-table :data="detailExp" border size="small">
            <el-table-column prop="year" label="年份" width="80" />
            <el-table-column prop="competitionName" label="赛项/赛道名称" />
            <el-table-column prop="category" label="类别" width="80" />
            <el-table-column prop="position" label="职务" width="120" />
          </el-table>
        </div>
        <div class="info-section"><h3>表彰/荣誉</h3>
          <div v-for="(h, i) in detailHonors" :key="i">{{ i+1 }}. {{ h.content || '-' }}</div>
        </div>
      </div>
    </el-dialog>

    <el-dialog title="审核操作" :visible.sync="auditVisible" width="450px">
      <el-form :model="auditForm">
        <el-form-item label="审核结果">
          <el-tag :type="auditForm.status === 'approved' ? 'success' : 'danger'" size="medium">{{ auditForm.status === 'approved' ? '通过' : '退回' }}</el-tag>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input v-model="auditForm.comment" type="textarea" :rows="3" placeholder="请输入审核意见（可选）" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="auditVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAudit">确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getApplications, getAdminApplicationDetail, auditApplication } from '@/api/application'

export default {
  name: 'AdminPage',
  data() {
    return {
      loading: false,
      applications: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      keyword: '',
      statusFilter: 'pending',
      detailVisible: false,
      detailRow: null,
      detailBasic: {},
      detailInfoDataObj: {},
      detailExp: [],
      detailHonors: [],
      auditVisible: false,
      auditedApp: null,
      auditForm: { status: '', comment: '' }
    }
  },
  computed: {
    totalCount() { return this.total },
    pendingCount() { return this.applications.filter(a => a.status === 'pending').length },
    approvedCount() { return this.applications.filter(a => a.status === 'approved').length },
    rejectedCount() { return this.applications.filter(a => a.status === 'rejected').length },
    detailBasicItems() {
      const b = this.detailBasic
      return [
        { label: '赛道', value: b.track }, { label: '性别', value: b.gender },
        { label: '民族', value: b.nationality }, { label: '政治面貌', value: b.politicalStatus },
        { label: '学历', value: b.education }, { label: '身体状况', value: b.healthStatus },
        { label: '职称', value: b.title }
      ]
    },
    detailInfoData() {
      const d = this.detailInfoDataObj
      return [
        { label: '姓名', value: d.name }, { label: '出生年月', value: d.birthDate },
        { label: '手机号', value: d.phone }, { label: '邮箱', value: d.email },
        { label: '工作单位', value: d.workUnit }, { label: '工作年限', value: d.workYears },
        { label: '职务', value: d.position }, { label: '专业方向', value: d.specialty },
        { label: '身份证号', value: d.idCard }
      ]
    }
  },
  methods: {
    loadData() {
      this.loading = true
      getApplications({
        page: this.currentPage,
        size: this.pageSize,
        keyword: this.keyword,
        status: this.statusFilter || 'all'
      }).then(res => {
        this.applications = res.data.records || []
        this.total = res.data.total || 0
      }).finally(() => { this.loading = false })
    },
    getBasicField(row, field) {
      try { return JSON.parse(row.basicInfo || '{}')[field] || '-' } catch { return '-' }
    },
    getDetailField(row, field) {
      try { return JSON.parse(row.detailedInfo || '{}')[field] || '-' } catch { return '-' }
    },
    statusLabel(status) {
      const map = { draft: '草稿', pending: '待审核', reviewing: '审核中', approved: '已通过', rejected: '已退回' }
      return map[status] || status
    },
    showDetail(row) {
      getAdminApplicationDetail(row.id).then(res => {
        this.detailRow = res.data
        try { this.detailBasic = JSON.parse(res.data.basicInfo || '{}') } catch { this.detailBasic = {} }
        try { this.detailInfoDataObj = JSON.parse(res.data.detailedInfo || '{}') } catch { this.detailInfoDataObj = {} }
        try { this.detailExp = JSON.parse(res.data.competitionExperiences || '[]') } catch { this.detailExp = [] }
        try { this.detailHonors = JSON.parse(res.data.honors || '[]') } catch { this.detailHonors = [] }
        this.detailVisible = true
      })
    },
    doAudit(row, status) {
      this.auditedApp = row
      this.auditForm = { status, comment: '' }
      this.auditVisible = true
    },
    confirmAudit() {
      auditApplication(this.auditedApp.id, this.auditForm).then(() => {
        this.$message.success('审核完成')
        this.auditVisible = false
        this.loadData()
      })
    }
  },
  mounted() {
    this.loadData()
  }
}
</script>

<style scoped>
.admin-page { padding: 24px 0 48px; }
.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.admin-header .section-title { margin-bottom: 0; border-bottom: none; }
.stat-row { margin: 20px 0; }
.stat-card {
  background: #f5f7ff;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
}
.stat-card.warning { background: #fff8e1; }
.stat-card.success { background: #e8f5e9; }
.stat-card.danger { background: #fce4ec; }
.stat-num { font-size: 28px; font-weight: 700; color: #1a237e; }
.stat-card.warning .stat-num { color: #e65100; }
.stat-card.success .stat-num { color: #2e7d32; }
.stat-card.danger .stat-num { color: #c62828; }
.stat-label { font-size: 13px; color: #888; margin-top: 4px; }
.filter-bar { margin-top: 8px; display: flex; align-items: center; }
.info-section { margin-bottom: 16px; padding: 12px; background: #fafafa; border-radius: 8px; }
.info-section h3 { font-size: 14px; color: #1a237e; margin: 0 0 10px; }
.info-item { margin-bottom: 6px; }
.info-label { font-size: 12px; color: #999; margin-right: 4px; }
.info-value { font-size: 13px; color: #333; }
</style>
