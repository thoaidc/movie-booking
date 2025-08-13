import {Component, EventEmitter, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html'
})
export class AuthComponent {
  @Output() loginSuccess = new EventEmitter<string>();
  isLogin = true;
  loginData = { email: '', password: '' };
  registerData = { name: '', email: '', password: '', confirmPassword: '' };

  toggleMode() {
    this.isLogin = !this.isLogin;
  }

  submitLogin() {
    console.log('Login data:', this.loginData);
    this.loginSuccess.emit(this.loginData.email);
  }

  submitRegister() {
    console.log('Register data:', this.registerData);
  }
}
