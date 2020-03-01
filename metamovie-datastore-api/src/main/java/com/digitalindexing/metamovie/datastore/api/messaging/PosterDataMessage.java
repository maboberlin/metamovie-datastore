package com.digitalindexing.metamovie.datastore.api.messaging;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PosterDataMessage {
  List<PosterDataMessageElement> posterDataMessageElementList;
}
