<template id="app-layout">
  <div class="app-layout">
    <div class="container-fluid">
      <!-- Start of navbar -->
      <nav class="navbar navbar-expand-lg" style="background-color: #237D31;">
        <div class="container-fluid">
          <a class="navbar-brand text-white fw-bold" href="/homepage">Health Now</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                  data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                  aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto">
              <li class="nav-item">
                <a class="nav-link text-white" href="/daily-goals-overview">Daily Goals</a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-white" href="/daily-habits-overview">Daily Habits</a>
              </li>
              <li class="nav-item">
                <a class="nav-link text-white" href="/milestones-overview">Milestones</a>
              </li>
            </ul>
            <div class="profile-section ms-auto dropdown">
              <a class="nav-link text-white d-flex align-items-center dropdown-toggle" href="#" id="profileDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="Profile" class="profile-image rounded-circle">
                <span class="profile-name ms-2">{{userName}}</span>
              </a>
              <ul class="dropdown-menu" aria-labelledby="profileDropdown">
                <li><a class="dropdown-item" href="/user-profile">Profile</a></li>
                <li><a class="dropdown-item" href="#" @click="logout">Logout</a></li>
              </ul>
            </div>
          </div>
        </div>
      </nav>
      <!--End of nav bar-->

      <!--Start of main content area-->
      <div class="content mt-3">
        <div class="container-fluid">
          <slot></slot>
        </div>
      </div>
      <!--End of main content area-->

      <!-- Start of footer -->
      <footer class="py-1 fixed-bottom" style="background-color: #237D31;">
        <div class="container text-center text-white">
          <p class="mb-0">© 2024 Health Now. All rights reserved.</p>
          <small>Follow us on
            <a href="#" class="text-white text-decoration-underline">Facebook</a>,
            <a href="#" class="text-white text-decoration-underline">Twitter</a>, and
            <a href="#" class="text-white text-decoration-underline">Instagram</a>.
          </small>
        </div>
      </footer>
      <!-- End of footer -->

    </div>
  </div>
</template>

<script>
app.component("app-layout", {
  template: "#app-layout",
  data: () => ({
  userName:null
  }),
  created() {
   this.userName = localStorage.getItem('userName');
  },
  methods: {
    logout() {

      localStorage.removeItem('userId');
      // Redirect to login page
      window.location.href = '/';

      // Prevent the user from navigating back to the homepage
      window.history.pushState(null, '', '/');
      window.addEventListener('popstate', () => {
        window.history.pushState(null, '', '/');
      });
    },
  },
});
</script>




<style>
/* Navbar styling */
.navbar {
  width: 100%;
  padding: 10px 20px;
}

/* Profile section styling */
.profile-section {
  display: flex;
  align-items: center;
}

.profile-image {
  width: 32px;
  height: 32px;
  margin-right: 8px;
}

.profile-name {
  font-size: 16px;
  font-weight: bold;
}

/* Text color */
.navbar-brand, .nav-link {
  color: white !important;
}

/* Navbar toggle icon color for small screens */
.navbar-toggler-icon {
  filter: invert(1);
}
</style>
