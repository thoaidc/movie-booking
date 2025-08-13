export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  phone: string;
  fullname: string;
}

export interface RegisterResponse {
  id: number;
  fullname: string;
  username: string;
  email: string;
  phone: string;
}

export interface LoginResponse {
  fullname: string;
  email: string;
  username: string;
  phone: string;
  token: string;
}
