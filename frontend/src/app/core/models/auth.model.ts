export interface User {
  username: string;
  token: string;
}

export interface AuthResponse {
  token: string;
  expiresIn: number;
}
