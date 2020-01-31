import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClient } from 'app/shared/model/client.model';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-client-detail',
  templateUrl: './client-detail.component.html'
})
export class ClientDetailComponent implements OnInit {
  client: IClient;
  user: IUser;

  constructor(protected activatedRoute: ActivatedRoute, private userService: UserService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      if (client.userId) {
        this.userService.findById(client.userId).subscribe(res => {
          this.user = res.body;
        });
      }
    });
  }

  previousState() {
    window.history.back();
  }
}
