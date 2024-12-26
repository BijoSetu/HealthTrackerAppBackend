<template id="user-profile">
  <div>

    <app-layout>
      <form v-if="user">
        <label class="col-form-label">User ID: </label>
        <input class="form-control" v-model="user.userid" name="id" type="number" readonly/><br>
        <label class="col-form-label">Name: </label>
        <input class="form-control" v-model="user.name" name="username" type="text"/><br>
        <label class="col-form-label">Email: </label>
        <input class="form-control" v-model="user.email" name="email" type="email"/><br>
      </form>
      <dt v-if="user">
        <br>
        <a :href="`/users/${user.userid}/daily-goals`">View User Activities</a>
      </dt>
    </app-layout>

  </div>
</template>

<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null
  }),
  created: function () {
    const userId = this.$javalin.pathParams["userId"];
    const url = `/api/users/id/${userId}`
    axios.get(url)
        .then(res => this.user = res.data.user)
        .catch(() => alert("Error while fetching user" + userId));
    console.log(this.user)
  }
});
</script>


