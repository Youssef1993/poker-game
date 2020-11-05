import {Injectable} from '@angular/core';
import {Constants} from '../constants';
import {Observable} from 'rxjs';
import {Value} from '../dtos/value';
import {HttpClient} from '@angular/common/http';
import {UserSubscription} from '../dtos/user-subscription';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly BASE_PATH = Constants.SERVER_URL + '/users';

  constructor(private http: HttpClient) {
  }

  public login(identifier: string, password: string): Observable<Value<string>> {
    const authenticationData: FormData = new FormData();
    authenticationData.append('identifier', identifier);
    authenticationData.append('password', password);
    return this.http.post<Value<string>>(this.BASE_PATH + '/login', authenticationData);
  }

  public isUsernameAvailable(username: string): Observable<Value<boolean>> {
    return this.http.get<Value<boolean>>(this.BASE_PATH + '/usernames/' + username);
  }

  public isEmailAvailable(email: string): Observable<Value<boolean>> {
    return this.http.get<Value<boolean>>(this.BASE_PATH + '/emails/' + email);
  }

  public createPlayerAccount(player: UserSubscription): Observable<Value<string>> {
    return this.http.post<Value<string>>(this.BASE_PATH, player);
  }

  public saveUserToken(token: string): void {
    localStorage.setItem('jwt', token);
  }

  isPlayerAuthenticated(): boolean {
    const token = localStorage.getItem('jwt');
    const tokenHelperService: JwtHelperService = new JwtHelperService();
    return token && !tokenHelperService.isTokenExpired(token);
  }

  logout(): void {
    localStorage.removeItem('jwt');
  }
}
