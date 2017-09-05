/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var app = angular.module('usuarioApp',['ngRoute','ngResource']);

app.config(function($routeProvider){
    
    $routeProvider.when('/cadastro',{
        controller: 'CadastroUsuarioControler',
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/cadastro/:id',{
        controller: 'CadastroUsuarioControler',
        templateUrl : 'templates/cadastro_usuario.html'
    }).when('/listagem',{
        controller: 'ListagemUsuarioControler',
        templateUrl : 'templates/listagem_usuario.html'
    }).otherwise('/listagem');
});


app.controller('ListagemUsuarioControler',function($scope,usuariosService){
    listarUsuarios();

    function listarUsuarios(){
        usuariosService.getUsuarios().then(function(usuarios){
            $scope.usuarios = usuarios;
        });
    };
    
    $scope.excluir = function(usuario) {
        usuariosService.excluir(usuario).then(function(){
            listarUsuarios();
        });
    };
});

app.controller('CadastroUsuarioControler',function($routeParams,$scope,$location,usuariosService){
    var id = $routeParams.id;
    
    if(id){
        usuariosService.getUsuario(id).then(function(usuarios){
            $scope.usuario = usuarios;
        });
    }else{
        $scope.usuario = {};
    }
    
    $scope.salvar = function(usuario) {
        usuariosService.salvarUsuario(usuario).then(function(){
            $location.path('listagem');
        });
        $scope.usuario = {};
    };

    $scope.cancelar = function(usuario) {
        $scope.usuario = {};
        $location.path('listagem');
    };
});


app.service('usuariosService',function(UsuariosResource) {


    this.getUsuario = function(id) {
        return UsuariosResource.get({id:id}).$promise;
    };

    this.getUsuarios = function() {
        return UsuariosResource.query().$promise;
    };

    this.salvarUsuario = function(usuario) {
        if (usuario.id){            
            return UsuariosResource.update({id : usuario.id},usuario).$promise;
        }else{
            return UsuariosResource.save(usuario).$promise;
        }

    };

    this.excluir = function(usuario) {
        return UsuariosResource.delete({id : usuario.id}).$promise;
    };  
});


app.factory('UsuariosResource',function($resource){
         return $resource('http://localhost:8084/condominio_backend/webresources/model.usuario/:id',{},{
                    update:{
                        method:'PUT'
                    }})
});