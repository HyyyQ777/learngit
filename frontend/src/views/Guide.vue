<template>
  <div class="guide-page">
    <div class="page-container">
      <div class="card-container">
        <h2 class="section-title">报名须知</h2>
        <div class="guide-content">
          <div class="guide-block">
            <h3><i class="el-icon-user-solid"></i> 推荐条件</h3>
            <ul>
              <li>具有良好的职业道德和敬业精神，遵纪守法，作风正派</li>
              <li>具备相关专业领域副高级及以上职称或高级技师职业资格</li>
              <li>具有5年以上相关专业领域工作经历</li>
              <li>近五年参与过市级及以上技能竞赛专家活动者优先</li>
              <li>身体健康，能胜任竞赛裁判、评审等工作</li>
            </ul>
          </div>
          <div class="guide-block">
            <h3><i class="el-icon-document"></i> 报名材料</h3>
            <ul>
              <li>填写完整的专家库人选推荐表（在线填写）</li>
              <li>近期竞赛专家活动经历（近五年）</li>
              <li>所获表彰或荣誉称号（3项以内）</li>
              <li>专家所在单位审核意见及盖章</li>
            </ul>
          </div>
          <div class="guide-block">
            <h3><i class="el-icon-time"></i> 审核流程</h3>
            <ul>
              <li>第一步：专家在线报名，填写推荐表并提交</li>
              <li>第二步：专家所在单位审核推荐</li>
              <li>第三步：西红市教育研究院专业（学科）审核</li>
              <li>第四步：西红市教育研究院分管部门审核</li>
              <li>第五步：西红市教育研究院最终审批</li>
            </ul>
          </div>
          <div class="guide-block">
            <h3><i class="el-icon-warning-outline"></i> 注意事项</h3>
            <ul>
              <li>请确保填写信息真实、准确、完整</li>
              <li>报名信息提交后将进入审核流程，请耐心等待</li>
              <li>若有疑问，请联系西红市教育研究院</li>
            </ul>
          </div>
        </div>
        <div class="form-actions">
          <el-button type="primary" round @click="$router.push('/register')">开始报名</el-button>
          <el-button round @click="$router.push('/')">返回首页</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GuidePage'
}
</script>

<style scoped>
.guide-page {
  padding: 32px 0;
}
.guide-content {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}
.guide-block {
  padding: 20px;
  background: #f8f9ff;
  border-radius: 10px;
  border-left: 4px solid #1e88e5;
}
.guide-block h3 {
  font-size: 16px;
  color: #1a237e;
  margin: 0 0 12px;
  display: flex;
  align-items: center;
  gap: 8px;
}
.guide-block ul {
  padding-left: 20px;
}
.guide-block li {
  font-size: 13px;
  color: #666;
  line-height: 2;
}
</style>
