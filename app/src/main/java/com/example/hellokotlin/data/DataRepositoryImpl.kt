package com.example.hellokotlin.data

import com.example.hellokotlin.data.session.Session
import com.example.hellokotlin.data.session.SessionManager
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.ApiService
import com.example.hellokotlin.data.network.cache.CacheManager
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import com.example.hellokotlin.data.network.model.LoginRequest
import com.example.hellokotlin.data.network.model.RateRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepositoryImpl @Inject constructor(private val apiService:ApiService,
                                             private val sessionManager: SessionManager,
                                             private val cacheManger: CacheManager
) : DataRepository {

    companion object{
        private val TAG = "DataRepositoryImpl"
    }

    /********************************************************/
    /********************* GLOBAL ***************************/
    /********************************************************/

    override suspend fun configuration(): Flow<Resource<ConfigurationResponse>> {
        return flow {
            emit(Resource.Loading())
            emit(Resource.Success(apiService.configuration()))
        }.catch {
            emit(Resource.Error())
        }.flowOn(Dispatchers.IO)
    }
    /********************************************************/
    /********************* LOGIN ****************************/
    /********************************************************/
    override suspend fun loadLastSession():Flow<Resource<User>>{
        return flow {
            val session = sessionManager.loadSession()
            var user:User ?= null
            if(session.isValid()){
                user = User(name = session.username)
            }
            emit(Resource.Success(user))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun login(username:String, password:String):Flow<Resource<User>> {
        return flow  {
            emit(Resource.Loading())
            val requestToken: String = apiService.requestToken().request_token
            val tokenResponse = apiService.login(LoginRequest(username,password,requestToken))
            val sessionIdResponse = apiService.createSessionId(tokenResponse)
            if(!sessionIdResponse.success || sessionIdResponse.sessionId == null) {
                emit(Resource.Error(Resource.Error.ErrorCode.INVALID_TOKEN))
            }else {
                val s = Session(username, sessionIdResponse.sessionId)
                sessionManager.storeSession(s)
                emit(Resource.Success(User(name = username)))
            }
        }.catch {
            emit(Resource.Error())
        }.flowOn((Dispatchers.IO))
    }

    override suspend fun logout(): Flow<Resource<Boolean>> {
        return flowOf(Resource.Success(true)).map {
            sessionManager.clear()
            it
        }
    }

    /********************************************************/
    /********************* MOVIES ***************************/
    /********************************************************/

    override suspend fun getMovieById(id:Int): Flow<Resource<Movie>> {
        // if is not in cache than there's some problem
        return cacheManger.getMovieByIdAsFlow(id)
            .map {
                Resource.Success(it)
            }.catch {
                Resource.Error<Movie>()
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovies():Flow<Resource<List<Movie>>>{
        return flow {
            if(!cacheManger.cacheValidator.isValidMovieCache()) {
                emit(Resource.Loading(cacheManger.getMoviesAsFlow().firstOrNull()))
                val response = apiService.popularMovies()
                val list = response.results?.toMutableList()
                list.forEach {
                    it.accountState =
                        apiService.accountState(it.id, sessionManager.loadSession().sessionId)
                }
                cacheManger.updateMovies(list)
            }
            val dbElements = cacheManger.getMoviesAsFlow()
                .map {
                    Resource.Success(it)
                }
            emitAll(dbElements)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun rateMovie(movie: Movie, rate:Int): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading())
            apiService.rateMovie(movie.id, sessionManager.loadSession().sessionId, RateRequest(rate))
            movie.accountState = apiService.accountState(movie.id, sessionManager.loadSession().sessionId)
            cacheManger.updateMovieAndAccountState(movie)
            emit(Resource.Success(true))
        }.flowOn(Dispatchers.IO)
    }

    /********************************************************/
    /*********************** USERS **************************/
    /********************************************************/

    override suspend fun getPopularUsers():Resource<List<User>>{
        return withContext(Dispatchers.IO){
            try {
                val items = apiService.popularUsers().results
                Resource.Success(items)
            }catch (e:Exception){
                Resource.Error()
            }
        }
    }



}