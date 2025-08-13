import {Component, EventEmitter, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {UsersService} from '../../core/services/users.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html'
})
export class AuthComponent {
  @Output() loginSuccess = new EventEmitter<string>();
  isLogin = true;
  loginData = { username: '', password: '' };
  registerData = { phone: '', fullname: '', username: '', email: '', password: '', confirmPassword: '' };

  constructor(private userService: UsersService, private toast: ToastrService) {
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
  }

  submitLogin() {
    this.userService.login(this.loginData).subscribe(response => {
      if (response.status && response.result) {
        let token = response.result.token;
        localStorage.setItem("token", token);
        localStorage.setItem("username", response.result.username);
        this.loginSuccess.emit(this.loginData.username);
        this.toast.success("Đăng nhập thành công");
      } else {
        this.toast.error(response.message);
      }
    });
  }

  submitRegister() {
    this.userService.register(this.registerData).subscribe(response => {
      if (response.status && response.result) {
        this.isLogin = true;
        this.toast.success("Đăng ký thành công");
      } else {
        this.toast.error(response.message);
      }
    });
  }
}
