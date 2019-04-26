app.controller('registerUserController', function ($scope, $http, $location, $route) {
  $scope.submitUserForm = function () {
    $http({
      method: 'POST',
      url: 'http://localhost:8080/api/users/',
      data: $scope.user,
    }).then(function (response) {
      $location.path("/list-all-users");
      $route.reload();
    }, function (errResponse) {
      $scope.errorMessage = errResponse.data.errorMessage;
    });
  }
  $scope.resetForm = function () {
    $scope.user = null;
  };
});


app.controller('homeController', function ($scope, $http, $location, $route) {
  $scope.pageable = {
    page: 0,
    perPage: 2
  };
  $http({
    method: 'GET',
    url: 'http://localhost:8080/api/users/paginate',
    params: {
      page: $scope.pageable.page,
      perPage: $scope.pageable.perPage
    }
  }).then(function (response) {
    $scope.paginateUsers = response.data.content;
    $scope.totalPages = response.data.totalPages;
  });
  $scope.nextPage = function () {
    $scope.pageable.page++;
    $http({
      method: 'GET',
      url: 'http://localhost:8080/api/users/paginate',
      params: {
        page: $scope.pageable.page,
        perPage: $scope.pageable.perPage
      }
    }).then(function (response) {
      $scope.paginateUsers = response.data.content;
      $scope.totalPages = response.data.totalPages;
    });
  }
  $scope.previousPage = function () {
    $scope.pageable.page--;
    $http({
      method: 'GET',
      url: 'http://localhost:8080/api/users/paginate',
      params: {
        page: $scope.pageable.page,
        perPage: $scope.pageable.perPage
      }
    }).then(function (response) {
      $scope.paginateUsers = response.data.content;
      $scope.totalPages = response.data.totalPages;
    });
  }
  $scope.editUser = function (userId) {
    $location.path("/update-user/" + userId);
  }
  $scope.deleteUser = function (userId) {
    $http({
      method: 'DELETE',
      url: 'http://localhost:8080/api/users/' + userId
    }).then(function (response) {
      $location.path("/list-allusers");
      $route.reload();
    });
  }
});


app.controller('listUserController', function ($scope, $http, $location, $route) {
  $http({
    method: 'GET',
    url: 'http://localhost:8080/api/users/'
  }).then(function (response) {
    $scope.users = response.data;
  });
  $scope.editUser = function (userId) {
    $location.path("/update-user/" + userId);
  }
  $scope.deleteUser = function (userId) {
    $http({
      method: 'DELETE',
      url: 'http://localhost:8080/api/users/' + userId
    }).then(function (response) {
      $location.path("/list-allusers");
      $route.reload();
    });
  }
});


app.controller('usersDetailsController', function ($scope, $http, $location,
  $routeParams, $route) {
  $scope.userId = $routeParams.id;
  $http({
    method: 'GET', url: 'http://localhost:8080/api/users/' +
      $scope.userId
  }).then(function (response) {
    $scope.user = response.data;
  });
  $scope.submitUserForm = function () {
    $http({
      method: 'POST', url: 'http://localhost:8080/api/users/',
      data: $scope.user,
    }).then(function (response) {
      $location.path("/list-all-users");
      $route.reload();
    }, function (errResponse) {
      $scope.errorMessage = "Error while updating User - Error Message: '" +
        errResponse.data.errorMessage;
    });
  }
});