<template id="daily-goals-overview">
  <div>
    <app-layout>

      <h3>DailyGoals list </h3>

      <div v-if="loading" class="d-flex justify-content-center">
        <dotlottie-player
            src="https://lottie.host/87024446-999a-4516-ab14-42e439110b20/uyuoHWJO8A.lottie"
            background="transparent"
            speed="1"
            style="width: 100px; height: 100px"
            loop
            autoplay
        ></dotlottie-player>
      </div>

      <div class="row">

        <div class="col-md-4" v-for="dailygoal in dailygoals" :key="dailygoal.id">

          <div class="card mb-4">

            <div class="card-header bg-secondary text-white">
              <h5 class="card-title">Goal ID: {{ dailygoal.id }}</h5>
            </div>
            <div class="card-body">
              <ul>
                <li>
                  {{dailygoal.id}}: {{dailygoal.goalDescription}}<br> Goal created at {{new Date(dailygoal.createdAt).toLocaleDateString()}}
                </li>
              </ul>

            </div>
          </div>
        </div>
      </div>

    </app-layout>

  </div>
</template>

<script>
app.component("daily-goals-overview", {
  template: "#daily-goals-overview",
  data: () => ({
    dailygoals: [],
    loading: true
  }),
  created() {
    // Set loading to true before making the API call
    const userId = localStorage.getItem('userId');
    console.log('Retrieved User ID:', userId);
    this.loading = true;
    axios.get(`/api/users/${userId}/daily-goals`)
        .then(res => {
          this.dailygoals = res.data;
          this.loading = false; // Set loading to false once the data is loaded
        })
        .catch(() => {
          alert("Error while fetching activities");
          this.loading = false; // Set loading to false in case of error
        });
  }
});
</script>
