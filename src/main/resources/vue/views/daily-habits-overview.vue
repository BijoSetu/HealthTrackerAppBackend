<template id="daily-habits-overview">
  <div>
    <app-layout>
      <div class="container mt-4">
        <h3 class="text-left mb-4">Daily Habits List</h3>

        <!--      if the loading is true it will show a lottie loading animation, for ux-->

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
          <div class="col-md-4" v-for="dailyHabit in dailyHabits" :key="dailyHabit.id">
            <div class="card mb-4 shadow-sm">
              <div class="card-header bg-secondary text-white">
                <h5 class="card-title">Habit ID: {{ dailyHabit.id }}</h5>
              </div>
<!--              this will show the dailyhabits in a card according to each date-->
              <div class="card-body">
                <p><strong>Date:</strong> {{new Date(dailyHabit.date).toLocaleDateString() }}</p>
                <p><strong>Sleep Time:</strong> {{ dailyHabit.hoursSlept }} hours</p>
                <p><strong>Steps Walked:</strong> {{ dailyHabit.stepsWalked }} steps</p>
                <p><strong>Calories Burned:</strong> {{ dailyHabit.caloriesBurned }} kcal</p>
                <p><strong>Total Time Exercised:</strong> {{ dailyHabit.totalTimeExercised }} minutes</p>
                <p><strong>Water Intake:</strong> {{ dailyHabit.waterIntakeMl }} mL</p>
                <p><strong>Calorie Intake:</strong> {{ dailyHabit.calorieIntake }} kcal</p>
                <p><strong>Protein Intake:</strong> {{ dailyHabit.proteinIntakeG }} g</p>
                <p><strong>Carbohydrates Intake:</strong> {{ dailyHabit.carbsIntakeG }} g</p>
                <p><strong>Screen Time:</strong> {{ dailyHabit.screenTimeMinutes }} minutes</p>
                <p><strong>Caffeine Intake:</strong> {{ dailyHabit.caffeineIntakeMg }} mg</p>
                <p><strong>Alcohol Intake:</strong> {{ dailyHabit.alcoholIntakeMl }} mL</p>
              </div>
<!--              card footer shows the created date-->
              <div class="card-footer text-muted">
                <small>Data updated on: {{ new Date(dailyHabit.date).toLocaleDateString() }}</small>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 100px;"></div>
    </app-layout>
  </div>
</template>

<script>
app.component("daily-habits-overview", {
  template: "#daily-habits-overview",
  data: () => ({
    dailyHabits: [],
    loading: true
  }),
  created() {
    // set loading to true before making the api call
    this.loading = true;
    // get the user id from the local storage
    const userId = localStorage.getItem('userId');
    console.log('Retrieved User ID:', userId);
    console.log("Component created, fetching daily habits...");

    axios.get(`/api/users/${userId}/daily-habits`)
        .then(res => {
          // logging the response
          console.log("Response received:", res);
          this.dailyHabits = res.data;
          // logging the dailyhabits list
          console.log("Daily habits data:", this.dailyHabits);
          this.loading=false;
        })
        .catch(error => {
          // logging error
          console.error("Error while fetching activities:", error);
          alert("Error while fetching activities");
        });
  }
});
</script>

<style>
.daily-habits-container {
  height: 100%;
  width: 100%;
  overflow-y: auto;
  padding: 10px;
  box-sizing: border-box;
}

ul {
  margin: 0;
  padding: 0;
  list-style-type: none;
}

li {
  margin-bottom: 20px;
}
.card {
  border-radius: 10px;
}
.card-header {
  background-color: #717171;
}
.card-footer {
  font-size: 0.875rem;
}
</style>