import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IUser, UserService, User } from 'app/core';
import { LOGIN_ALREADY_USED_TYPE, EMAIL_ALREADY_USED_TYPE } from 'app/shared';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['client-update.scss']
})
export class ClientUpdateComponent implements OnInit {
  client: IClient;
  user: IUser;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    userid: [],
    login: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    email: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    localite: [null, [Validators.required]],
    compte: [],
    userId: [],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]
  });
  doNotMatch: string;
  error: any;
  errorUserExists: any;
  errorEmailExists: any;
  success: boolean;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clientService: ClientService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ client }) => {
      this.updateForm(client);
      this.client = client;
      this.getUserAndUpdateForm(client.userId);
    });
    // remove me
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  getUserAndUpdateForm(id: number) {
    if (id) {
      this.userService.findById(id).subscribe(res => {
        this.updateUserForm(res.body);
        this.user = res.body;
      });
    }
  }

  updateForm(client: IClient) {
    this.editForm.patchValue({
      id: client.id,
      adresse: client.adresse,
      localite: client.localite,
      compte: client.compte,
      userId: client.userId
    });
  }

  updateUserForm(user: IUser) {
    this.editForm.patchValue({
      userid: user.id,
      firstName: user.firstName,
      lastName: user.lastName,
      login: user.login,
      email: user.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const client = this.createFromForm();
    let user = this.createUserFromForm();
    user = this.setUserFixedFields(user);
    if (client.id !== undefined) {
      // edit Mode
      client.userId = user.id;
      this.userService.update(user).subscribe(() => {
        this.subscribeToSaveResponse(this.clientService.update(client));
      });
      // this.subscribeToSaveResponse(this.userService.update(user));
    } else {
      // create mode
      this.register(user, client);
      // this.userService.create(user).subscribe(res=> {
      //   client.userId = res.body.id;
      //   this.subscribeToSaveResponse(this.clientService.create(client));
      // });
    }
  }

  register(user: IUser, client: IClient) {
    let registerAccount = {};
    const login = this.editForm.get(['login']).value;
    const email = this.editForm.get(['email']).value;
    const password = this.editForm.get(['password']).value;
    if (password !== this.editForm.get(['confirmPassword']).value) {
      this.doNotMatch = 'ERROR';
    } else {
      registerAccount = { ...user, password };
      this.doNotMatch = null;
      this.error = null;
      this.errorUserExists = null;
      this.errorEmailExists = null;

      this.clientService.save(registerAccount).subscribe(
        res => {
          // creating client frm user
          console.log(res);
          client.userId = res.body.id;
          this.subscribeToSaveResponse(this.clientService.create(client));
          this.success = true;
        },
        response => this.processError(response)
      );
    }
  }
  private processError(response: HttpErrorResponse) {
    this.success = null;
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = 'ERROR';
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = 'ERROR';
    } else {
      this.error = 'ERROR';
    }
    this.isSaving = false;
    console.log('here..');
  }

  private createFromForm(): IClient {
    const entity = {
      ...new Client(),
      id: this.editForm.get(['id']).value,
      adresse: this.editForm.get(['adresse']).value,
      localite: this.editForm.get(['localite']).value,
      // categorie: this.editForm.get(['categorie']).value,
      compte: this.editForm.get(['compte']).value
      // userId: this.editForm.get(['userId']).value
    };
    return entity;
  }
  // AI CODE
  private createUserFromForm(): IUser {
    const entity = {
      ...new User(),
      id: this.editForm.get(['userid']).value,
      login: this.editForm.get(['login']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      activated: true,
      authorities: ['ROLE_USER'],
      langKey: 'en'
    };
    return entity;
  }

  private setUserFixedFields(user: IUser): IUser {
    user.activated = true;
    user.authorities = ['ROLE_USER'];
    user.langKey = 'en';
    return user;
  }

  // subscribe to obserables
  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
    result.subscribe((res: HttpResponse<any>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
