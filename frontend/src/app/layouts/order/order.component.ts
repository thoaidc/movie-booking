import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {BookingService} from '../../core/services/bookings.service';
import {Order} from '../../core/models/bookings.model';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  constructor(private bookingService: BookingService) {}

  orders: Order[] = [];

  getStatusClass(status: string): string {
    switch (status) {
      case 'PENDING':
        return 'bg-warning text-dark';
      case 'RESERVE_SEATS_SUCCESS':
      case 'PAYMENT_SUCCESS':
      case 'COMPLETED':
        return 'bg-success';
      case 'RESERVE_SEATS_FAILED':
      case 'PAYMENT_FAILED':
      case 'FAILED':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }

  statusMap: Record<string, string> = {
    PENDING: 'Chờ xử lý',
    RESERVE_SEATS_SUCCESS: 'Đặt ghế thành công',
    RESERVE_SEATS_FAILED: 'Đặt ghế thất bại',
    PAYMENT_SUCCESS: 'Thanh toán thành công',
    PAYMENT_FAILED: 'Thanh toán thất bại',
    COMPLETED: 'Hoàn tất',
    FAILED: 'Thất bại'
  };

  ngOnInit(): void {
    this.bookingService.getOrders(Number.parseInt(localStorage.getItem('userId') || '0') || 0).subscribe(response => {
      if (response) {
        this.orders = response.result || [];
      }
    })
  }
}
