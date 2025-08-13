import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ApplicationConfigService} from '../config/application-config.service';
import {API_LOGIN, API_REGISTER} from '../../constants/api.constants';
import {Observable} from 'rxjs';
import {BaseResponse} from '../models/response.model';
import {LoginRequest, LoginResponse, RegisterRequest, RegisterResponse} from '../models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class UsersService {

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  private loginApi = this.applicationConfigService.getEndpointFor(API_LOGIN);
  private registerApi = this.applicationConfigService.getEndpointFor(API_REGISTER);

  login(request: LoginRequest): Observable<BaseResponse<LoginResponse>> {
    return this.http.post<BaseResponse<LoginResponse>>(`${this.loginApi}`, request);
  }

  register(request: RegisterRequest): Observable<BaseResponse<RegisterResponse>> {
    return this.http.post<BaseResponse<RegisterResponse>>(`${this.registerApi}`, request);
  }
}
