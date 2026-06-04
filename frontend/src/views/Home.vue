<template>
  <div class="home-page">
    <section class="hero-section">
      <h1 class="hero-title">西红市职业院校技能大赛</h1>
      <p class="hero-subtitle">专家库人选推荐系统 · 专业化人才选拔平台</p>
      <div class="hero-actions">
        <el-button type="primary" round size="medium" class="hero-btn" @click="startRegister">开始报名</el-button>
        <el-button round size="medium" class="hero-btn-secondary" @click="$router.push('/guide')">查看报名须知</el-button>
      </div>
    </section>

    <div class="page-container">
      <div class="card-grid">
        <div class="feature-card" @click="startRegister">
          <div class="feature-icon" style="background: linear-gradient(135deg, #1e88e5, #42a5f5)">
            <i class="el-icon-edit-outline"></i>
          </div>
          <h3>在线报名</h3>
          <p>填写个人信息、竞赛经历及专业资质，一键提交报名申请</p>
          <span class="card-link">立即报名 →</span>
        </div>

        <div class="feature-card" @click="$router.push('/guide')">
          <div class="feature-icon" style="background: linear-gradient(135deg, #7c4dff, #b388ff)">
            <i class="el-icon-reading"></i>
          </div>
          <h3>报名须知</h3>
          <p>了解报名条件、所需材料、审核流程及注意事项</p>
          <span class="card-link">查看详情 →</span>
        </div>

        <div class="feature-card" @click="$router.push('/login')">
          <div class="feature-icon" style="background: linear-gradient(135deg, #00bfa5, #4db6ac)">
            <i class="el-icon-user-solid"></i>
          </div>
          <h3>登录管理</h3>
          <p>已报名专家登录查看报名状态，教练/管理员审核报名材料</p>
          <span class="card-link">立即登录 →</span>
        </div>
      </div>

      <div class="info-section card-container">
        <h2 class="section-title">推荐流程</h2>
        <el-steps :active="4" align-center>
          <el-step title="在线报名" description="填写推荐表信息" />
          <el-step title="单位审核" description="专家所在单位审核" />
          <el-step title="教研员审核" description="教育研究院专业审核" />
          <el-step title="研究院审批" description="教育研究院最终审批" />
          <el-step title="入库公示" description="入选专家库公示" />
        </el-steps>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  methods: {
    startRegister() {
      if (localStorage.getItem('token')) {
        this.$router.push('/register')
      } else {
        this.$router.push('/login')
      }
    }
  }
}
</script>

<style scoped>
.hero-actions {
  display: flex;
  gap: 16px;
  justify-content: center;
}
.hero-btn {
  height: 44px;
  border-radius: 22px;
  padding: 0 40px;
  font-size: 15px;
  background: #fff !important;
  color: #1a237e !important;
  border: none !important;
  font-weight: 600;
}
.hero-btn-secondary {
  height: 44px;
  border-radius: 22px;
  padding: 0 40px;
  font-size: 15px;
  border: 2px solid rgba(255,255,255,0.6) !important;
  color: #fff !important;
  background: transparent !important;
}
.card-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-top: -40px;
  position: relative;
  z-index: 10;
}
.feature-card {
  background: #fff;
  border-radius: 16px;
  padding: 32px 24px;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0,0,0,0.06);
  cursor: pointer;
  transition: all 0.3s;
}
.feature-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 32px rgba(0,0,0,0.1);
}
.feature-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
  font-size: 28px;
  color: #fff;
}
.feature-card h3 {
  font-size: 18px;
  color: #333;
  margin: 0 0 8px;
}
.feature-card p {
  font-size: 13px;
  color: #888;
  line-height: 1.6;
  margin: 0 0 12px;
}
.card-link {
  color: #1e88e5;
  font-size: 13px;
  font-weight: 500;
}
.info-section {
  margin-top: 32px;
}
</style>
