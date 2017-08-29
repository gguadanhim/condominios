/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('usuarioApp',['ngRoute']);

app.config(function($routeProvider){
    
    $routeProvider.when('/cadastro',{
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/listagem',{
        templateUrl : 'templates/listagem_usuario.html'
    }).otherwise('/listagem');
});

app.controller('UsuarioControler',function($scope,usuariosService){

    $scope.usuario = {};

    listarUsuarios();

    function listarUsuarios(){
        usuariosService.getUsuarios().then(function(resposta){
            $scope.usuarios = resposta.data;
        });
    };

    $scope.salvar = function(usuario) {
        usuariosService.salvarUsuario(usuario).then(function(){
            listarUsuarios();
        });
        $scope.usuario = {};
    };

    $scope.excluir = function(usuario) {
        usuariosService.excluir(usuario).then(function(){
            listarUsuarios();
        });
    };

    $scope.editar = function(usuario) {
        $scope.usuario  = angular.copy(usuario);
    };
    $scope.cancelar = function(usuario) {
        $scope.usuario = {};
    };
});


app.service('usuariosService',function($http) {
    var url = 'http://localhost:8084/condominio_backend/webresources/model.usuario';

    this.getUsuarios = function() {
        return $http.get(url);
    };

    this.salvarUsuario = function(usuario) {
        if (usuario.id){            
            return $http.put(url+'/'+ usuario.id, usuario);
        }else{
            return $http.post(url,usuario);
        }

    };

    this.excluir = function(usuario) {
        return $http.delete(url+'/'+usuario.id);
    };  
});