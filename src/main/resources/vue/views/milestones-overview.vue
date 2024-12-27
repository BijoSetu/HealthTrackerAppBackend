<template id="milestones-overview">
  <div>
    <app-layout>


      <div class="container mt-4">
        <h3 class="text-left mb-4">Milestones List</h3>

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
          <div class="col-md-4" v-for="milestone in milestones" :key="milestone.id">
            <div class="card mb-4 shadow-sm">
              <div class="card-header bg-secondary text-white">
                <h5 class="card-title">Milestone ID: {{ milestone.id}}</h5>
              </div>
              <div class="card-body">
                <ul>
                  <li>
                    {{milestone.id}}: {{milestone.milestoneName}} milestone created at {{new Date(milestone.created).toLocaleDateString()}}
                  </li>
                </ul>
              </div>

            </div>
          </div>
        </div>
      </div>

    </app-layout>

  </div>
</template>

<script>
app.component("milestones-overview",{
  template: "#milestones-overview",
  data: () => ({
    milestones: [],
    loading:true
  }),
  created() {
    const userId = localStorage.getItem('userId');
    console.log('Retrieved User ID:', userId);
    axios.get(`/api/users/${userId}/milestones`)
        .then(res => {
          this.milestones = res.data.milestones;
        this.loading = false;
        })
        .catch(() => alert("Error while fetching activities"));
  }
});
</script>
