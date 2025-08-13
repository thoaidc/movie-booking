import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {ApplicationConfigService} from '../config/application-config.service';
import {
  API_CREATE_BOOKING
} from '../../constants/api.constants';
import {Booking} from '../models/bookings.model';
import {Observable} from 'rxjs';
import {BaseResponse} from '../models/response.model';

@Injectable({
  providedIn: 'root',
})
export class BookingService {

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  private createBookingApi = this.applicationConfigService.getEndpointFor(API_CREATE_BOOKING);

  createBookingRequest(bookingRequest: Booking): Observable<BaseResponse<any>> {
    return this.http.post<BaseResponse<any>>(this.createBookingApi, bookingRequest);
  }
}
