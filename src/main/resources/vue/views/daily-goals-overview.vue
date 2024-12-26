<template id="user-daily-goals-overview">
  <div>
    <app-layout>

      <h3>DailyGoals list </h3>
      <ul>
        <li v-for="dailygoal in dailygoals">
          {{dailygoal.id}}: {{dailygoal.goalDescription}} goal created at {{dailygoal.createdAt}}
        </li>
      </ul>
    </app-layout>

  </div>
</template>

<script>
app.component("user-daily-goals-overview",{
  template: "#user-daily-goals-overview",
  data: () => ({
    dailygoals: [],
  }),
  created() {
    const userId = this.$javalin.pathParams["userId"];
    axios.get(`/api/users/${userId}/daily-goals`)
        .then(res => this.dailygoals = res.data)
        .catch(() => alert("Error while fetching activities"));
  }
});
</script>
